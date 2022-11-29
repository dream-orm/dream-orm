package com.moxa.dream.template.mapper;

import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.util.InvokerUtil;

import java.util.List;

public class UpdateNonByIdMapper extends UpdateByIdMapper {
    public UpdateNonByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected String getUpdateParam(List<String> setList) {
        return InvokerUtil.wrapperInvokerSQL(SystemInvokerFactory.NAMESPACE, SystemInvokerFactory.NON, ",", super.getUpdateParam(setList));
    }
}
