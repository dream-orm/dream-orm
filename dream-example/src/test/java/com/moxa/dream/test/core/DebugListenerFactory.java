package com.moxa.dream.test.core;

import com.moxa.dream.module.core.listener.Listener;
import com.moxa.dream.module.core.listener.factory.AbstractListenerFactory;

public class DebugListenerFactory extends AbstractListenerFactory {
    @Override
    protected Listener[] getDefaultListener() {
        return new Listener[0];
    }
}
