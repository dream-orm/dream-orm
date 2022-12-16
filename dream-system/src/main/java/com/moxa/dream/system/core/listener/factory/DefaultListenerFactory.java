package com.moxa.dream.system.core.listener.factory;

import com.moxa.dream.system.core.listener.*;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultListenerFactory implements ListenerFactory {
    private QueryListener[] queryListeners = new QueryListener[0];
    private InsertListener[] insertListeners = new InsertListener[0];
    private UpdateListener[] updateListeners = new UpdateListener[0];
    private DeleteListener[] deleteListeners = new DeleteListener[0];
    private BatchListener[] batchListeners = new BatchListener[0];

    @Override
    public void listeners(Listener... listeners) {
        List<QueryListener> queryListenerList = new ArrayList<>();
        List<InsertListener> insertListenerList = new ArrayList<>();
        List<UpdateListener> updateListenerList = new ArrayList<>();
        List<DeleteListener> deleteListenerList = new ArrayList<>();
        List<BatchListener> batchListenerList = new ArrayList<>();
        if (!ObjectUtil.isNull(listeners)) {
            for (Listener listener : listeners) {
                if (listener instanceof QueryListener) {
                    queryListenerList.add((QueryListener) listener);
                }
                if (listener instanceof InsertListener) {
                    insertListenerList.add((InsertListener) listener);
                }
                if (listener instanceof UpdateListener) {
                    updateListenerList.add((UpdateListener) listener);
                }
                if (listener instanceof DeleteListener) {
                    deleteListenerList.add((DeleteListener) listener);
                }
                if (listener instanceof BatchListener) {
                    batchListenerList.add((BatchListener) listener);
                }
            }
            queryListeners = copyTo(queryListeners, queryListenerList.toArray(new QueryListener[0]));
            insertListeners = copyTo(insertListeners, insertListenerList.toArray(new InsertListener[0]));
            updateListeners = copyTo(updateListeners, updateListenerList.toArray(new UpdateListener[0]));
            deleteListeners = copyTo(deleteListeners, deleteListenerList.toArray(new DeleteListener[0]));
            batchListeners = copyTo(batchListeners, batchListenerList.toArray(new BatchListener[0]));
        }
    }

    public <T extends Listener> T[] copyTo(T[] targetListeners, T[] sourceListeners) {
        if (!ObjectUtil.isNull(sourceListeners)) {
            int length = targetListeners.length;
            targetListeners = Arrays.copyOf(targetListeners, length + sourceListeners.length);
            System.arraycopy(sourceListeners, 0, targetListeners, length, sourceListeners.length);
        }
        return targetListeners;
    }

    @Override
    public QueryListener[] getQueryListener() {
        return queryListeners;
    }

    @Override
    public UpdateListener[] getUpdateListener() {
        return updateListeners;
    }

    @Override
    public InsertListener[] getInsertListener() {
        return insertListeners;
    }

    @Override
    public DeleteListener[] getDeleteListener() {
        return deleteListeners;
    }

    @Override
    public BatchListener[] getBatchListener() {
        return batchListeners;
    }
}
