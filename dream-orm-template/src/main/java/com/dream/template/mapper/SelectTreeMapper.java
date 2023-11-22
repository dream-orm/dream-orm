package com.dream.template.mapper;

import com.dream.system.core.action.DestroyAction;
import com.dream.system.core.session.Session;
import com.dream.util.tree.Tree;
import com.dream.util.tree.TreeUtil;

import java.util.Collection;

public class SelectTreeMapper extends SelectListMapper {

    public SelectTreeMapper(Session session) {
        super(session);
    }

    @Override
    protected DestroyAction[] destroyActions() {
        return new DestroyAction[]{(result, mappedStatement, session) -> TreeUtil.toTree((Collection<? extends Tree>) result)};
    }
}
