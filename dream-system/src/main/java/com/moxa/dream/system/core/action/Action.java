package com.moxa.dream.system.core.action;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;

import java.util.Properties;

public interface Action {
    default void setProperties(Properties properties) {

    }

    void doAction(Session session, MappedStatement mappedStatement, Object arg) throws Exception;
}
