package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.annotation.Ignore;
import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.template.util.TemplateUtil;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class SelectSqlMapper extends AbstractSqlMapper {
    public SelectSqlMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type) {
        Set<String> tableSet = TemplateUtil.getTableSet(type);
        String suffix = getSuffix(tableInfo);
        String sql = "select " + InvokerUtil.wrapperInvokerSQL(
                SystemInvokerFactory.NAMESPACE,
                SystemInvokerFactory.ALL,
                ",") + " from " +
                InvokerUtil.wrapperInvokerSQL(
                        SystemInvokerFactory.NAMESPACE,
                        SystemInvokerFactory.TABLE,
                        ",",
                        tableSet.toArray(new String[0])
                ) + " " + suffix;
        return new MethodInfo.Builder(configuration)
                .rowType(getRowType())
                .colType(type)
                .sql(sql)
                .build();
    }

    protected abstract String getSuffix(TableInfo tableInfo);

    protected abstract Class<? extends Collection> getRowType();
}
