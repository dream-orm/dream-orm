package com.moxa.dream.drive.provider;

import com.moxa.dream.system.core.action.Action;

@FunctionalInterface
public interface ActionProvider {
    default Action[] init() {
        return null;
    }

    default Action[] loop() {
        return null;
    }

    default Action[] destroy() {
        return null;
    }

    String sql();

}
