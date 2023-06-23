package com.moxa.dream.system.core.listener.factory;

import com.moxa.dream.system.core.listener.Listener;

public interface ListenerFactory {
    void listeners(Listener... listeners);

    Listener[] getListeners();

}
