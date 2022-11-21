package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.session.SessionFactory;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ActionExecutor implements Executor {
    protected Executor nextExecutor;

    public ActionExecutor(Executor nextExecutor) {
        this.nextExecutor = nextExecutor;
    }

    @Override
    public Object query(MappedStatement mappedStatement) throws SQLException {
        return execute(mappedStatement, (ms) -> nextExecutor.query(mappedStatement));
    }

    @Override
    public Object update(MappedStatement mappedStatement) throws SQLException {
        return execute(mappedStatement, (ms) -> nextExecutor.update(mappedStatement));
    }

    @Override
    public Object insert(MappedStatement mappedStatement) throws SQLException {
        return execute(mappedStatement, (ms) -> nextExecutor.insert(mappedStatement));
    }

    @Override
    public Object delete(MappedStatement mappedStatement) throws SQLException {
        return execute(mappedStatement, (ms) -> nextExecutor.delete(mappedStatement));
    }

    protected Object execute(MappedStatement mappedStatement, Function<MappedStatement, Object> function) throws SQLException {
        Action[] initActionList = mappedStatement.getInitActionList();
        Action[] destroyActionList = mappedStatement.getDestroyActionList();
        if (!ObjectUtil.isNull(initActionList)) {
            doActions(initActionList, mappedStatement.getArg());
        }
        Object result;
        result = function.apply(mappedStatement);
        if (!ObjectUtil.isNull(destroyActionList)) {
            doActions(destroyActionList, mappedStatement.getArg());
        }
        return result;
    }

    @Override
    public Object batch(List<MappedStatement> mappedStatements) throws SQLException {
        return nextExecutor.batch(mappedStatements);
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

    @Override
    public SessionFactory getSessionFactory() {
        return nextExecutor.getSessionFactory();
    }


    protected void doActions(Action[] actions, Object arg) {
        try {
            for (Action action : actions) {
                action.doAction(this, arg);
            }
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
    }

}
