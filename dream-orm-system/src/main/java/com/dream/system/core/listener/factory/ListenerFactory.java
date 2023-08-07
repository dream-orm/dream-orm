package com.dream.system.core.listener.factory;

import com.dream.system.core.listener.Listener;

public interface ListenerFactory {
    void listeners(Listener... listeners);

    Listener[] getListeners();

}
