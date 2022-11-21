package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.action.Action;
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
                    columnList.add(columnInfo.getColumn());
                    valueList.add(InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", DREAM_TEMPLATE_PARAM + "." + columnInfo.getName()));
                }
            }
        }
        String sql = "insert into " + table + "(" + String.join(",", columnList) + ")values(" + String.join(",", valueList) + ")";
        return new MethodInfo.Builder(configuration)
                .rowType(NonCollection.class)
                .colType(Integer.class)
                .columnNames(getColumnNames(tableInfo))
                .initActionList(initActionList(tableInfo))
                .destroyActionList(destroyActionList(tableInfo))
                .sql(sql)
                .build();
    }

    @Override
    protected boolean accept(WrapType wrapType) {
        return (CODE & wrapType.getCode()) > 0;
    }

    protected Action[] initActionList(TableInfo tableInfo) {
        return new Action[0];
    }

    protected Action[] destroyActionList(TableInfo tableInfo) {
        return new Action[0];
    }

    protected String[] getColumnNames(TableInfo tableInfo) {
        return new String[0];
    }
}
