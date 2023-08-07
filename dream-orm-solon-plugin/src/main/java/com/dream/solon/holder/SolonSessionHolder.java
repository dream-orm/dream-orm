package com.dream.solon.holder;

import com.dream.system.core.session.Session;
import com.dream.system.core.session.SessionFactory;
import com.dream.template.session.SessionHolder;
import org.noear.solon.data.tran.TranUtils;

public class SolonSessionHolder implements SessionHolder {
    private SessionFactory sessionFactory;

    public SolonSessionHolder(SessionFactory sessionFactory) {
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
        return TranUtils.inTrans();
    }
}
