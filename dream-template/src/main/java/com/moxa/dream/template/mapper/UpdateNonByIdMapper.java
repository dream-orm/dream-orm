package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;
import com.moxa.dream.system.core.session.Session;

import java.util.List;

public class UpdateNonByIdMapper extends UpdateByIdMapper {
    public UpdateNonByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected String getUpdateParam(List<String> setList) {
        return InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.NON, ",", super.getUpdateParam(setList));
    }
}
