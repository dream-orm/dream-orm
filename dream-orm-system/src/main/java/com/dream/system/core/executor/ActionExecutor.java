package com.dream.system.core.executor;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.action.DestroyAction;
import com.dream.system.core.action.InitAction;
import com.dream.system.core.action.LoopAction;
import com.dream.system.core.session.Session;
import com.dream.util.common.ObjectUtil;

import java.sql.SQLException;
import java.util.Collection;

public class ActionExecutor implements Executor {
    protected Executor nextExecutor;

    public ActionExecutor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

    @Override
    public Object execute(MappedStatement mappedStatement, Session session) throws SQLException {
        InitAction[] initActionList = mappedStatement.getInitActionList();
        LoopAction[] loopActionList = mappedStatement.getLoopActionList();
        DestroyAction[] destroyActionList = mappedStatement.getDestroyActionList();
        if (!ObjectUtil.isNull(initActionList)) {
            for (InitAction action : initActionList) {
                action.init(mappedStatement, session);
            }
        }
        Object result = nextExecutor.execute(mappedStatement, session);
        if (result != null && !ObjectUtil.isNull(loopActionList)) {
            if (result instanceof Collection) {
                for (LoopAction loopAction : loopActionList) {
                    for (Object row : (Collection) result) {
                        loopAction.loop(row, mappedStatement, session);
                    }
                }
            } else {
                for (LoopAction loopAction : loopActionList) {
                    loopAction.loop(result, mappedStatement, session);
                }
            }
        }
        if (!ObjectUtil.isNull(destroyActionList)) {
            for (DestroyAction destroyAction : destroyActionList) {
                result = destroyAction.destroy(result, mappedStatement, session);
            }
        }
        return result;
    }

    @Override
    public boolean isAutoCommit() {
        return nextExecutor.isAutoCommit();
    }

    @Override
    public void commit() {
        nextExecutor.commit();
    }

    @Override
    public void rollback() {
        nextExecutor.rollback();
    }

    @Override
    public void close() {
        nextExecutor.close();
    }
}
