package com.moxa.dream.drive.provider;

import com.moxa.dream.system.core.action.Action;

import java.lang.reflect.Method;

public interface ActionProvider {
    default Action[] init(Method method) {
        return null;
    }

    default Action[] loop(Method method) {
        return null;
    }

    default Action[] destroy(Method method) {
        return null;
    }

    default Integer timeOut(Method method){
        return null;
    }

    String value(Method method);

}
