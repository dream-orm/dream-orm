package com.moxa.dream.driver.factory;

import com.moxa.dream.module.core.listener.Listener;
import com.moxa.dream.module.core.listener.factory.AbstractListenerFactory;

public class DefaultListenerFactory extends AbstractListenerFactory {
    @Override
    protected Listener[] getDefaultListener() {
        return new Listener[0];
    }
}
