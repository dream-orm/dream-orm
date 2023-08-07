package com.dream.system.core.listener.factory;

import com.dream.system.core.listener.Listener;

public class DefaultListenerFactory implements ListenerFactory {
    private Listener[] listeners;

    @Override
    public void listeners(Listener... listeners) {
        this.listeners = listeners;
    }

    @Override
    public Listener[] getListeners() {
        return listeners;
    }

}
