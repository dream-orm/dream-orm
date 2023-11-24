package com.dream.template.mapper;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.StarInvoker;
import com.dream.system.config.Command;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.action.DestroyAction;
import com.dream.system.core.action.InitAction;
import com.dream.system.core.action.LoopAction;
import com.dream.system.core.session.Session;
import com.dream.system.table.TableInfo;
import com.dream.system.util.SystemUtil;

import java.util.Collection;

public abstract class SelectMapper extends ValidateMapper {

    public SelectMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getValidateMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg) {
        String sql = "select " + getSelectColumn(type) + " from " +
                SystemUtil.getTableName(type) + " " + getOther(configuration, tableInfo, type, arg);
        return new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(getRowType())
                .setColType(getColType(type))
                .setSql(sql)
                .addInitAction(initActions())
                .addLoopAction(loopActions())
                .addDestroyAction(destroyActions());
    }

    protected String getSelectColumn(Class<?> type) {
        return AntlrUtil.invokerSQL(StarInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
    }

    protected abstract String getOther(Configuration configuration, TableInfo tableInfo, Class<?> type, Object arg);

    protected abstract Class<? extends Collection> getRowType();

    protected Class<?> getColType(Class type) {
        return type;
    }

    protected String getFromTable(Class type) {
        return SystemUtil.getTableName(type);
    }

    @Override
    protected Command getCommand() {
        return Command.QUERY;
    }

    protected InitAction[] initActions() {
        return null;
    }

    protected LoopAction[] loopActions() {
        return null;
    }

    protected DestroyAction[] destroyActions() {
        return null;
    }
}
