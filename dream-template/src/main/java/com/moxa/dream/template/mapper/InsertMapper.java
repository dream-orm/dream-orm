package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.annotation.WrapType;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InsertMapper extends WrapMapper {
    private final int CODE = 1;

    /**/
    public InsertMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo doGetMethodInfo(Configuration configuration, TableInfo tableInfo, List<Field> fieldList, Object arg) {
        String table = tableInfo.getTable();
        List<String> columnList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                String name = field.getName();
                ColumnInfo columnInfo = tableInfo.getColumnInfo(name);
                if (columnInfo != null) {
                    String column = columnInfo.getColumn();
                    String invokerSQL = InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", DREAM_TEMPLATE_PARAM + "." + columnInfo.getName());
                    if (columnInfo.isPrimary()) {
                        columnList.add(0, column);
                        valueList.add(0, invokerSQL);
                    } else {
                        columnList.add(column);
                        valueList.add(invokerSQL);
                    }
                }
            }
        }
        String sql = "insert into " + table + "(" + String.join(",", columnList) + ")values(" + String.join(",", valueList) + ")";
        return getMethodInfo(configuration, tableInfo, sql);
    }

    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, String sql) {
        return new MethodInfo.Builder(configuration)
                .rowType(NonCollection.class)
                .colType(Integer.class)
                .sql(sql)
                .build();
    }

    @Override
    protected boolean accept(WrapType wrapType) {
        return (CODE & wrapType.getCode()) > 0;
    }

}
