package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.sql.SQLException;
import java.util.List;

public class ActionExecutor implements Executor {
    protected Executor nextExecutor;

    public ActionExecutor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

    @Override
    public Object query(MappedStatement mappedStatement, Session session) throws SQLException {
        return execute(mappedStatement, (ms) -> nextExecutor.query(mappedStatement, session), session);
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

    protected Object execute(MappedStatement mappedStatement, Function<MappedStatement, Object> function, Session session) throws SQLException {
        Action[] initActionList = mappedStatement.getInitActionList();
        Action[] destroyActionList = mappedStatement.getDestroyActionList();
        doActions(initActionList, mappedStatement, mappedStatement.getArg(), session);
        Object result;
        result = function.apply(mappedStatement);
        doActions(destroyActionList, mappedStatement, result, session);
        return result;
    }

    @Override
    public Object batch(List<MappedStatement> mappedStatements, Session session) throws SQLException {
        return nextExecutor.batch(mappedStatements, session);
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
        if (!ObjectUtil.isNull(actions)) {
            try {
                for (Action action : actions) {
                    action.doAction(session, mappedStatement, arg);
                }
            } catch (Exception e) {
                throw new DreamRunTimeException(e);
            }
        }
    }
}
