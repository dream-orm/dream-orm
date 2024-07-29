package com.dream.system.antlr.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.config.Configuration;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.ObjectWrapper;
import com.dream.util.exception.DreamRunTimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InsertMapInvoker extends AbstractInvoker {
    public static final String FUNCTION = "insertMap";

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        if (columnList == null || columnList.length != 2) {
            throw new DreamRunTimeException("@" + FUNCTION + "参数个数必须为2");
        }
        String tableName = toSQL.toStr(columnList[0], assist, invokerList);
        String property = toSQL.toStr(columnList[1], assist, invokerList);
        ObjectWrapper wrapper = assist.getCustom(ObjectWrapper.class);
        Object obj = wrapper.get(property);
        if (obj == null) {
            throw new DreamRunTimeException("插入对象不能为空");
        } else if (!(obj instanceof Map)) {
            throw new DreamRunTimeException("插入对象必须为Map");
        }
        Map<String, Object> objMap = (Map<String, Object>) obj;
        Configuration configuration = assist.getCustom(Configuration.class);
        TableFactory tableFactory = configuration.getTableFactory();
        TableInfo tableInfo = tableFactory.getTableInfo(tableName);
        List<String> columns = new ArrayList<>();
        List<String> columnRefs = new ArrayList<>();
        Set<String> columnSet = objMap.keySet();
        for (String column : columnSet) {
            columnRefs.add(column);
            if (tableInfo != null) {
                ColumnInfo columnInfo = tableInfo.getColumnInfo(column);
                if (columnInfo == null) {
                    throw new DreamRunTimeException("表" + tableName + "不存在字段" + column);
                }
                column = columnInfo.getColumn();
            }
            columns.add(column);
        }
        InsertStatement insertStatement = SystemUtil.insertStatement(tableName, columns, property, columnRefs);
        String sql = toSQL.toStr(insertStatement, assist, invokerList);
        invokerStatement.replaceWith(insertStatement);
        return sql;
    }

    @Override
    public String function() {
        return FUNCTION;
    }
}
