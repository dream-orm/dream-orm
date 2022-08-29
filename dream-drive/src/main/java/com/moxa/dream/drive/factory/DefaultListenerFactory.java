package com.moxa.dream.drive.factory;

import com.moxa.dream.system.core.listener.Listener;
import com.moxa.dream.system.core.listener.factory.AbstractListenerFactory;

public class DefaultListenerFactory extends AbstractListenerFactory {
    @Override
    protected Listener[] getDefaultListener() {
        return new Listener[0];
    }
}
