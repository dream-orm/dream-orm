package com.dream.system.antlr.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.config.Configuration;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InsertInvoker extends AbstractInvoker {
    public static final String FUNCTION = "insert";

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        ObjectWrapper wrapper = assist.getCustom(ObjectWrapper.class);
        String property = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        Object obj = wrapper.get(property);
        if (obj == null) {
            throw new DreamRunTimeException("插入对象不能为空");
        }
        Configuration configuration = assist.getCustom(Configuration.class);
        List<Field> fieldList = ReflectUtil.findField(obj.getClass());
        String tableName = SystemUtil.getTableName(obj.getClass());
        if (ObjectUtil.isNull(tableName)) {
            throw new DreamRunTimeException(obj.getClass().getName() + "未绑定表");
        }
        TableFactory tableFactory = configuration.getTableFactory();
        TableInfo tableInfo = tableFactory.getTableInfo(tableName);
        if (tableInfo == null) {
            throw new DreamRunTimeException("表'" + tableName + "'未在TableFactory注册");
        }
        List<String> columns = new ArrayList<>();
        List<String> columnRefs = new ArrayList<>();
        for (Field field : fieldList) {
            String name = field.getName();
            ColumnInfo columnInfo = tableInfo.getColumnInfo(name);
            if (columnInfo != null) {
                columns.add(columnInfo.getColumn());
                columnRefs.add(property + "." + columnInfo.getName());
            }
        }
        InsertStatement insertStatement = SystemUtil.insertStatement(tableName, columns, Collections.singletonList(columnRefs));
        String sql = toSQL.toStr(insertStatement, assist, invokerList);
        invokerStatement.replaceWith(insertStatement);
        return sql;
    }

    @Override
    public String function() {
        return FUNCTION;
    }
}
