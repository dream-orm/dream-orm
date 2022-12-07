package com.moxa.dream.system.core.action;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;

public interface Action {
    void doAction(Session session, MappedStatement mappedStatement, Object arg) throws Exception;
}
