package com.moxa.dream.template.session;

import com.moxa.dream.system.core.session.Session;

public interface SessionHolder {
    Session getSession();

    boolean isSessionTransactional(Session session);

    void closeSession(Session session);
}
