package com.dream.template.mapper;

import com.dream.system.config.MethodInfo;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.resultsethandler.TransformResultSetHandler;
import com.dream.system.core.session.Session;
import com.dream.util.tree.TreeUtil;

import java.util.Collection;
import java.util.List;

public class SelectTreeMapper extends SelectListMapper {
    private ResultSetHandler resultSetHandler;

    public SelectTreeMapper(Session session) {
        super(session);
        resultSetHandler = new TransformResultSetHandler<Collection, List>(result-> TreeUtil.toTree(result));
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg) {
        methodInfo.setResultSetHandler(resultSetHandler);
        return super.execute(methodInfo, arg);
    }
}
