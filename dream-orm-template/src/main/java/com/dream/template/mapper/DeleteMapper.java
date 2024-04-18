package com.dream.template.mapper;

import com.dream.system.config.Command;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.table.TableInfo;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.NonCollection;

public abstract class DeleteMapper extends ValidateMapper {
    public DeleteMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getValidateMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg) {
        String table = tableInfo.getTable();
        String other = getOther(configuration, tableInfo, arg);
        String sql = "delete from " + SystemUtil.transfer(table) + " " + other;
        return new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(NonCollection.class)
                .setColType(Integer.class)
                .setSql(sql);
    }

    @Override
    protected Command getCommand() {
        return Command.DELETE;
    }

    protected abstract String getOther(Configuration configuration, TableInfo tableInfo, Object arg);
}
