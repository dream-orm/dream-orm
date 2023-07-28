package com.moxa.dream.solon.holder;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.core.session.SessionFactory;
import com.moxa.dream.template.session.SessionHolder;
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
