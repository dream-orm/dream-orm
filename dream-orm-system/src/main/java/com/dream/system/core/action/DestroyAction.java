package com.dream.system.core.action;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;

public interface DestroyAction {
    Object destroy(Object result, MappedStatement mappedStatement, Session session);
}
