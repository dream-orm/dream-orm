package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.reflection.util.NonCollection;
import jdk.nashorn.internal.ir.annotations.Ignore;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class InsertSqlMapper extends AbstractSqlMapper {
    public InsertSqlMapper(Session session) {
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
                        valueList.add(InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", columnInfo.getName()));
                    }
                }
            }
        }
        String sql = "insert into `" + table + "`(" + String.join(",", columnList) + ")values(" + String.join(",", valueList) + ")";
        return new MethodInfo.Builder(configuration)
                .rowType(NonCollection.class)
                .colType(Integer.class)
                .columnNames(getColumnNames(tableInfo))
                .initActionList(initActionList(tableInfo))
                .destroyActionList(destroyActionList(tableInfo))
                .batch(isBatch())
                .sql(sql)
                .build();
    }


    protected boolean ignore(Field field) {
        return field.isAnnotationPresent(Ignore.class);
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

    protected boolean isBatch() {
        return false;
    }
}
