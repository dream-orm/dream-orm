package com.moxa.dream.test.core;

import com.moxa.dream.module.listener.Listener;
import com.moxa.dream.module.listener.factory.AbstractListenerFactory;

public class DebugListenerFactory extends AbstractListenerFactory {
    @Override
    protected Listener[] getDefaultListener() {
        return new Listener[0];
    }
}
