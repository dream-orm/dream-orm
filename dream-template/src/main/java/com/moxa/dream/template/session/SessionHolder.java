package com.moxa.dream.template.session;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.core.session.SessionFactory;

public interface SessionHolder {
    Session getSession(SessionFactory sessionFactory);

    boolean isSessionTransactional(Session session);

    void closeSession(Session session);
}
