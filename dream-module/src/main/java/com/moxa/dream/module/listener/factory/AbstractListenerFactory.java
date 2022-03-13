package com.moxa.dream.module.listener.factory;

import com.moxa.dream.module.listener.*;

public class AbstractListenerFactory implements ListenerFactory {

    @Override
    public void listener(Listener[] listeners) {

    }

    @Override
    public QueryListener[] getQueryListener() {
        return new QueryListener[0];
    }

    @Override
    public UpdateListener[] getUpdateListener() {
        return new UpdateListener[0];
    }

    @Override
    public InsertListener[] getInsertListener() {
        return new InsertListener[0];
    }

    @Override
    public DeleteListener[] getDeleteListener() {
        return new DeleteListener[0];
    }
}
