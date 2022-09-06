package com.moxa.dream.drive.provider;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.action.Action;

import java.lang.reflect.Method;

public interface ActionProvider {
    default Action[] init(Configuration configuration, Method method) {
        return null;
    }

    default Action[] loop(Configuration configuration, Method method) {
        return null;
    }

    default Action[] destroy(Configuration configuration, Method method) {
        return null;
    }

    String value(Configuration configuration, Method method);

}
