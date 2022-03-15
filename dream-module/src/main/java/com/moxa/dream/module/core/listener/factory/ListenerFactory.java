package com.moxa.dream.module.core.listener.factory;

import com.moxa.dream.module.core.listener.*;

public interface ListenerFactory {
    void listener(Listener[] listeners);

    QueryListener[] getQueryListener();

    UpdateListener[] getUpdateListener();

    InsertListener[] getInsertListener();

    DeleteListener[] getDeleteListener();
}
