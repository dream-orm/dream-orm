package com.dream.template.mapper;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.NonInvoker;
import com.dream.system.core.session.Session;

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
