package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.SQLException;
import java.util.Collection;

public class ActionExecutor implements Executor {
    protected Executor nextExecutor;

    public ActionExecutor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

    @Override
    public Object query(MappedStatement mappedStatement, Session session) throws SQLException {
        return execute(mappedStatement, (ms) -> {
            Object result = nextExecutor.query(mappedStatement, session);
            if (result != null) {
                Action[] loopActionList = mappedStatement.getLoopActionList();
                if (!ObjectUtil.isNull(loopActionList)) {
                    if (result instanceof Collection) {
                        Collection resultCollection = (Collection) result;
                        if (!resultCollection.isEmpty()) {
                            for (Object arg : resultCollection) {
                                doActions(loopActionList, mappedStatement, arg, session);
                            }
                        }
                    } else {
                        doActions(loopActionList, mappedStatement, result, session);
                    }
                }
            }
            return result;
        }, session);
    }

    @Override
    public Object update(MappedStatement mappedStatement, Session session) throws SQLException {
        return execute(mappedStatement, (ms) -> nextExecutor.update(mappedStatement, session), session);
    }

    @Override
    public Object insert(MappedStatement mappedStatement, Session session) throws SQLException {
        return execute(mappedStatement, (ms) -> nextExecutor.insert(mappedStatement, session), session);
    }

    @Override
    public Object delete(MappedStatement mappedStatement, Session session) throws SQLException {
        return execute(mappedStatement, (ms) -> nextExecutor.delete(mappedStatement, session), session);
    }

    @Override
    public Object batch(BatchMappedStatement batchMappedStatement, Session session) throws SQLException {
        return execute(batchMappedStatement, (ms) -> nextExecutor.batch(batchMappedStatement, session), session);
    }

    protected Object execute(MappedStatement mappedStatement, Function<MappedStatement, Object> function, Session session) throws SQLException {
        Action[] initActionList = mappedStatement.getInitActionList();
        Action[] destroyActionList = mappedStatement.getDestroyActionList();
        if (!ObjectUtil.isNull(initActionList)) {
            doActions(initActionList, mappedStatement, mappedStatement.getArg(), session);
        }
        Object result;
        result = function.apply(mappedStatement);
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
