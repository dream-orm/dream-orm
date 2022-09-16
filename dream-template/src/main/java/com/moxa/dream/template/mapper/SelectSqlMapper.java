package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.annotation.Ignore;
import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class SelectSqlMapper extends AbstractSqlMapper {
    public SelectSqlMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type) {
        List<String> columnList = new ArrayList<>();
        List<String> tableList = new ArrayList<>();
        deepField(configuration.getTableFactory(), type, columnList, tableList);
        String suffix = getSuffix(tableInfo);
        String sql = "select " + String.join(",", columnList) + " from " + InvokerUtil.wrapperInvokerSQL(SystemInvokerFactory.NAMESPACE, SystemInvokerFactory.TABLE, ",", tableList.toArray(new String[0])) + " " + suffix;
        return new MethodInfo.Builder(configuration)
                .rowType(getRowType())
                .colType(type)
                .sql(sql)
                .build();
    }

    protected void deepField(TableFactory tableFactory, Class<?> type, List<String> columnList, List<String> tableList) {
        String table = getTable(type);
        if (!ObjectUtil.isNull(table)) {
            TableInfo tableInfo = tableFactory.getTableInfo(table);
            if (tableInfo != null) {
                table = tableInfo.getTable();
                if (!tableList.contains(table)) {
                    tableList.add(table);
                    List<Field> fieldList = ReflectUtil.findField(type);
                    if (!ObjectUtil.isNull(fieldList)) {
                        for (Field field : fieldList) {
                            if (!ignore(field)) {
                                ColumnInfo columnInfo = tableInfo.getColumnInfo(field.getName());
                                if (columnInfo != null) {
                                    columnList.add("`" + table + "`.`" + columnInfo.getColumn() + "`");
                                } else {
                                    deepField(tableFactory, ReflectUtil.getColType(field.getType(), field), columnList, tableList);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    protected boolean ignore(Field field) {
        return field.isAnnotationPresent(Ignore.class);
    }

    protected abstract String getSuffix(TableInfo tableInfo);

    protected abstract Class<? extends Collection> getRowType();
}
