package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.SQLException;

public class ActionExecutor implements Executor {
    protected Executor nextExecutor;

    public ActionExecutor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

    @Override
    public Object execute(MappedStatement mappedStatement, Session session) throws SQLException {
        Action[] initActionList = mappedStatement.getInitActionList();
        Action[] destroyActionList = mappedStatement.getDestroyActionList();
        if (!ObjectUtil.isNull(initActionList)) {
            doActions(initActionList, mappedStatement, mappedStatement.getArg(), session);
        }
        Object result = nextExecutor.execute(mappedStatement, session);
        if (!ObjectUtil.isNull(destroyActionList)) {
            doActions(destroyActionList, mappedStatement, result, session);
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

    protected void doActions(Action[] actions, MappedStatement mappedStatement, Object arg, Session session) {
        for (Action action : actions) {
            action.doAction(session, mappedStatement, arg);
        }

    }
}
