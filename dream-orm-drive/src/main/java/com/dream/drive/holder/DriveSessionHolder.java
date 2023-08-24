package com.dream.drive.holder;

import com.dream.drive.transaction.TransManager;
import com.dream.system.core.session.Session;
import com.dream.system.core.session.SessionFactory;
import com.dream.template.session.SessionHolder;

public class DriveSessionHolder implements SessionHolder {
    private SessionFactory sessionFactory;

    public DriveSessionHolder(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Session getSession() {
        return sessionFactory.openSession(false);
    }

    @Override
    public void closeSession(Session session) {
        session.close();
    }

    @Override
    public boolean isSessionTransactional(Session session) {
        return TransManager.isTrans();
    }
}
