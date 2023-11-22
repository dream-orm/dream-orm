package com.dream.system.core.action;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;

public interface InitAction {
    void init(MappedStatement mappedStatement, Session session);
}
