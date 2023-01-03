package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.template.resolve.MappedResolve;
import com.moxa.dream.template.resulthandler.TreeResultSetHandler;

public class SelectTreeMapper extends SelectListMapper {
    private ResultSetHandler resultSetHandler = new TreeResultSetHandler();

    public SelectTreeMapper(Session session) {
        super(session);
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg, MappedResolve mappedResolve) {
        methodInfo.setResultSetHandler(resultSetHandler);
        return super.execute(methodInfo, arg, mappedResolve);
    }
}
