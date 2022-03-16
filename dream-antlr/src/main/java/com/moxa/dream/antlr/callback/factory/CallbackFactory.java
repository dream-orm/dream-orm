package com.moxa.dream.antlr.callback.factory;

import com.moxa.dream.antlr.callback.Callback;

public interface CallbackFactory {
    Callback getCallback(String className);
}
