package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.util.TemplateUtil;

import java.util.Collection;
import java.util.Set;

public abstract class SelectTemplateMapper extends AbstractTemplateMapper {
    public SelectTemplateMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type) {
        Set<String> tableNameSet = TemplateUtil.getTableNameSet(type);
        String suffix = getSuffix(tableInfo);
        String sql = "select " + InvokerUtil.wrapperInvokerSQL(
                SystemInvokerFactory.NAMESPACE,
                SystemInvokerFactory.ALL,
                ",") + " from " +
                InvokerUtil.wrapperInvokerSQL(
                        SystemInvokerFactory.NAMESPACE,
                        SystemInvokerFactory.TABLE,
                        ",",
                        tableNameSet.toArray(new String[0])
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
