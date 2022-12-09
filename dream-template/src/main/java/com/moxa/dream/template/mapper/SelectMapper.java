package com.moxa.dream.template.mapper;

import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.util.InvokerUtil;
import com.moxa.dream.template.attach.AttachMent;
import com.moxa.dream.template.util.TemplateUtil;

import java.util.Collection;
import java.util.Set;

public abstract class SelectMapper extends AbstractMapper {
    private AttachMent attachMent;

    public SelectMapper(Session session, AttachMent attachMent) {
        super(session);
        this.attachMent = attachMent;
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg) {
        String sql = "select " + getSelectColumn(type) + " from " +
                getFromTable(type) + " " + getOther(configuration, tableInfo, type, arg);
        if (attachMent != null) {
            sql = sql + " " + attachMent.attach(configuration, tableInfo, arg != null ? arg.getClass() : null, Command.QUERY);
        }
        return new MethodInfo.Builder(configuration)
                .rowType(getRowType())
                .colType(getColType(type))
                .sql(sql)
                .build();
    }

    protected String getSelectColumn(Class<?> type) {
        return InvokerUtil.wrapperInvokerSQL(
                SystemInvokerFactory.NAMESPACE,
                SystemInvokerFactory.ALL,
                ",");
    }

    protected abstract String getOther(Configuration configuration, TableInfo tableInfo, Class<?> type, Object arg);

    protected abstract Class<? extends Collection> getRowType();

    protected Class<?> getColType(Class type) {
        return type;
    }

    protected String getFromTable(Class type) {
        Set<String> tableNameSet = TemplateUtil.getTableNameSet(type);
        return InvokerUtil.wrapperInvokerSQL(
                SystemInvokerFactory.NAMESPACE,
                SystemInvokerFactory.TABLE,
                ",",
                tableNameSet.toArray(new String[0])
        );
    }

}
