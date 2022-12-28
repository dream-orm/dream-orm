package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.system.antlr.invoker.NonInvoker;
import com.moxa.dream.system.core.session.Session;

import java.util.List;

public class UpdateNonByIdMapper extends UpdateByIdMapper {
    public UpdateNonByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected String getUpdateParam(List<String> setList) {
        return AntlrUtil.invokerSQL(NonInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, super.getUpdateParam(setList));
    }
}
