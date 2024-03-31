package com.dream.instruct.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.util.SystemUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TakeColumnInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_instruct_take_column";

    @Override
    public Invoker newInstance() {
        return new TakeColumnInvoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        TakeColumnInvokerStatement takeColumnInvokerStatement = (TakeColumnInvokerStatement) invokerStatement;
        Class entityType = takeColumnInvokerStatement.getEntityType();
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        List<Statement> statementList = null;
        if (methodInfo != null) {
            Configuration configuration = methodInfo.getConfiguration();
            if (configuration != null) {
                String tableName = SystemUtil.getTableName(entityType);
                if (tableName != null) {
                    TableFactory tableFactory = configuration.getTableFactory();
                    TableInfo tableInfo = tableFactory.getTableInfo(tableName);
                    if (tableInfo == null) {
                        throw new DreamRunTimeException("表'" + tableName + "'未在TableFactory注册");
                    }
                    List<Field> fieldList = ReflectUtil.findField(entityType);
                    statementList = new ArrayList<>(fieldList.size());
                    for (Field field : fieldList) {
                        if (!SystemUtil.ignoreField(field)) {
                            ColumnInfo columnInfo = tableInfo.getColumnInfo(field.getName());
                            if (columnInfo != null) {
                                statementList.add(new SymbolStatement.SingleMarkStatement(columnInfo.getColumn()));
                            }
                        }
                    }
                }
            }
        }
        if (statementList != null) {
            ListColumnStatement listColumnStatement = new ListColumnStatement(",");
            listColumnStatement.setColumnList(statementList);
            return toSQL.toStr(listColumnStatement, assist, invokerList);
        } else {
            return "*";
        }
    }

    @Override
    public Handler[] handler() {
        return new Handler[0];
    }
}
