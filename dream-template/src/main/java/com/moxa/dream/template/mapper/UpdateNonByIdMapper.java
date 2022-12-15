package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.system.antlr.invoker.NonInvoker;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.template.attach.AttachMent;

import java.util.List;

public class UpdateNonByIdMapper extends UpdateByIdMapper {
    public UpdateNonByIdMapper(Session session, AttachMent attachMent) {
        super(session, attachMent);
    }

    @Override
    protected String getUpdateParam(List<String> setList) {
        return AntlrUtil.invokerSQL(new NonInvoker(), super.getUpdateParam(setList));
    }
}
