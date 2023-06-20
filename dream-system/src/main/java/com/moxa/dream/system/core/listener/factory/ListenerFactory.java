package com.moxa.dream.system.core.listener.factory;

import com.moxa.dream.system.core.listener.*;

public interface ListenerFactory {
    void listeners(Listener... listeners);

    QueryListener[] getQueryListener();

    UpdateListener[] getUpdateListener();

    InsertListener[] getInsertListener();

    DeleteListener[] getDeleteListener();

    BatchListener[] getBatchListener();

    TruncateListener[] getTruncateListener();

    DropListener[] getDropListener();
}
