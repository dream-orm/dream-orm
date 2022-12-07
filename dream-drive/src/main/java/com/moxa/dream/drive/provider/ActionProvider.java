package com.moxa.dream.drive.provider;

import com.moxa.dream.system.core.action.Action;

import java.util.Collection;

public interface ActionProvider {

    String sql();

    default Action[] init() {
        return null;
    }

    default Action[] loop() {
        return null;
    }

    default Action[] destroy() {
        return null;
    }

    default Class<? extends Collection> rowType() {
        return null;
    }

    default Class<?> colType() {
        return null;
    }

    default Boolean cache() {
        return null;
    }

    default Integer timeOut() {
        return null;
    }
}
