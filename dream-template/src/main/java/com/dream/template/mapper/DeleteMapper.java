package com.dream.template.mapper;

import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.table.TableInfo;
import com.dream.util.common.NonCollection;

public abstract class DeleteMapper extends AbstractMapper {
    public DeleteMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg) {
        String table = tableInfo.getTable();
        String other = getOther(configuration, tableInfo, arg);
        String sql = "delete from " + table + " " + other;
        return new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(NonCollection.class)
                .setColType(Integer.class)
                .setSql(sql);
    }

    protected abstract String getOther(Configuration configuration, TableInfo tableInfo, Object arg);
}
