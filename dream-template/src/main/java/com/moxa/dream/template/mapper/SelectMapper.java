package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.system.antlr.invoker.AllInvoker;
import com.moxa.dream.system.antlr.invoker.TableInvoker;
import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
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
        return new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(getRowType())
                .setColType(getColType(type))
                .setSql(sql);
    }

    protected String getSelectColumn(Class<?> type) {
        return AntlrUtil.invokerSQL(AllInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
    }

    protected abstract String getOther(Configuration configuration, TableInfo tableInfo, Class<?> type, Object arg);

    protected abstract Class<? extends Collection> getRowType();

    protected Class<?> getColType(Class type) {
        return type;
    }

    protected String getFromTable(Class type) {
        Set<String> tableNameSet = TemplateUtil.getTableNameSet(type);
        return AntlrUtil.invokerSQL(TableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, tableNameSet.toArray(new String[0])
        );
    }

}
