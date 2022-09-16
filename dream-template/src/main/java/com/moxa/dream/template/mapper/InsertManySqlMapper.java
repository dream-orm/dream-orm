package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.reflection.util.NonCollection;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertManySqlMapper extends AbstractSqlMapper {
    private String param = "param";

    public InsertManySqlMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type) {
        String table = tableInfo.getTable();
        List<Field> fieldList = ReflectUtil.findField(type);
        List<String> columnList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                if (!ignore(field)) {
                    String name = field.getName();
                    ColumnInfo columnInfo = tableInfo.getColumnInfo(name);
                    if (columnInfo != null) {
                        columnList.add("`" + columnInfo.getColumn() + "`");
                        valueList.add(InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", "item." + columnInfo.getName()));
                    }
                }
            }
        }
        String sql = "insert into `" + table + "`(" + String.join(",", columnList) + ")values " + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.FOREACH, ",", param, "(" + String.join(",", valueList) + ")");
        return new MethodInfo.Builder(configuration)
                .rowType(NonCollection.class)
                .colType(Integer.class)
                .sql(sql)
                .build();
    }

    protected boolean ignore(Field field) {
        return field.isAnnotationPresent(Ignore.class);
    }

    @Override
    protected Object wrapArg(Object arg) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(param, arg);
        return paramMap;
    }
}
