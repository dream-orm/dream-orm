package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.annotation.Ignore;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.reflection.util.NonCollection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class UpdateSqlMapper extends AbstractSqlMapper {
    protected String param="param";
    public UpdateSqlMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type) {
        String table = tableInfo.getTable();
        List<Field> fieldList = ReflectUtil.findField(type);
        ColumnInfo primColumnInfo = tableInfo.getPrimColumnInfo();
        ObjectUtil.requireNonNull(primColumnInfo, "表'" + table + "'未注册主键");
        List<String> setList = new ArrayList<>();
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                if (!ignore(field)) {
                    String name = field.getName();
                    ColumnInfo columnInfo = tableInfo.getColumnInfo(name);
                    if (columnInfo != null) {
                        setList.add(columnInfo.getColumn() + "=" +
                                InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", param+"."+columnInfo.getName()));
                    }
                }
            }
        }
        String updateParam = getUpdateParam(setList);
        String suffix = getSuffix(tableInfo);
        String sql = "update " + table + " set " + updateParam + " " + suffix;
        return new MethodInfo.Builder(configuration)
                .rowType(NonCollection.class)
                .colType(Integer.class)
                .sql(sql)
                .build();
    }

    protected boolean ignore(Field field) {
        return field.isAnnotationPresent(Ignore.class);
    }

    protected abstract String getSuffix(TableInfo tableInfo);

    protected abstract String getUpdateParam(List<String> setList);
}
