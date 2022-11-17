package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectByIdsTemplateMapper extends SelectTemplateMapper {
    private String param = "param";

    public SelectByIdsTemplateMapper(Session session) {
        super(session);
    }

    @Override
    protected String getSuffix(TableInfo tableInfo) {
        ColumnInfo columnInfo = tableInfo.getPrimColumnInfo();
        ObjectUtil.requireNonNull(columnInfo, "表'" + tableInfo.getTable() + "'未注册主键");
        return "where " + tableInfo.getTable() + "." + columnInfo.getColumn() + " in(" + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.FOREACH, ",", param) + ")";
    }

    @Override
    protected Class<? extends Collection> getRowType() {
        return List.class;
    }

    @Override
    protected Map<String, Object> wrapArg(Object arg) {
        Map<String, Object> argMap = new HashMap<>();
        argMap.put(param, arg);
        return argMap;
    }
}
