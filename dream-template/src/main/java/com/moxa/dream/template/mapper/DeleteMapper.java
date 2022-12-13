package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.attach.AttachMent;
import com.moxa.dream.util.common.NonCollection;

public abstract class DeleteMapper extends AbstractMapper {
    private AttachMent attachMent;

    public DeleteMapper(Session session, AttachMent attachMent) {
        super(session);
        this.attachMent = attachMent;
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg) {
        String table = tableInfo.getTable();
        String other = getOther(configuration, tableInfo, arg);
        String sql = "delete from " + table + " " + other;
        if (attachMent != null) {
            sql = sql + " " + attachMent.attach(configuration, tableInfo, arg != null ? arg.getClass() : null, Command.DELETE);
        }
        return new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(NonCollection.class)
                .setColType(Integer.class)
                .setSql(sql);
    }

    protected abstract String getOther(Configuration configuration, TableInfo tableInfo, Object arg);
}
