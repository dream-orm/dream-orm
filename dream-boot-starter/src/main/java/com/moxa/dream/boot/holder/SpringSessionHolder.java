package com.moxa.dream.boot.holder;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.core.session.SessionFactory;
import com.moxa.dream.template.session.SessionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.ResourceHolderSupport;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class SpringSessionHolder implements SessionHolder {

    @Override
    public Session getSession(SessionFactory sessionFactory) {
        SessionHolderSupport holder = (SessionHolderSupport) TransactionSynchronizationManager.getResource(this);
        Session session = sessionHolder(holder);
        if (session != null) {
            return session;
        }
        session = sessionFactory.openSession(false);
        registerSessionHolder(session);
        return session;
    }

    @Override
    public void closeSession(Session session) {
        SessionHolderSupport holder = (SessionHolderSupport) TransactionSynchronizationManager.getResource(this);
        if ((holder != null) && (holder.getSession() == session)) {
            holder.released();
        } else {
            session.close();
        }
    }

    @Override
    public boolean isSessionTransactional(Session session) {
        SessionHolderSupport holder = (SessionHolderSupport) TransactionSynchronizationManager.getResource(this);
        return (holder != null) && (holder.getSession() == session);
    }

    private void registerSessionHolder(Session session) {
        SessionHolderSupport holder;
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            holder = new SessionHolderSupport(session);
            TransactionSynchronizationManager.bindResource(this, holder);
            TransactionSynchronizationManager
                    .registerSynchronization(new SessionSynchronization(holder, this));
            holder.setSynchronizedWithTransaction(true);
            holder.requested();
        }
    }

    private Session sessionHolder(SessionHolderSupport holder) {
        Session session = null;
        if (holder != null && holder.isSynchronizedWithTransaction()) {
            holder.requested();
            session = holder.getSession();
        }
        return session;
    }

    static class SessionSynchronization implements TransactionSynchronization {
        private final SessionHolderSupport holder;
        private final SessionHolder sessionHolder;
        private boolean holderActive = true;

        public SessionSynchronization(SessionHolderSupport holder, SessionHolder sessionHolder) {
            this.holder = holder;
            this.sessionHolder = sessionHolder;
        }

        @Override
        public int getOrder() {
            return DataSourceUtils.CONNECTION_SYNCHRONIZATION_ORDER - 1;
        }

        @Override
        public void suspend() {
            if (this.holderActive) {
                TransactionSynchronizationManager.unbindResource(this.sessionHolder);
            }
        }

        @Override
        public void resume() {
            if (this.holderActive) {
                TransactionSynchronizationManager.bindResource(this.sessionHolder, this.holder);
            }
        }

        @Override
        public void beforeCommit(boolean readOnly) {
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                this.holder.getSession().commit();
            }
        }

        @Override
        public void beforeCompletion() {
            if (!this.holder.isOpen()) {
                TransactionSynchronizationManager.unbindResource(sessionHolder);
                this.holderActive = false;
                this.holder.getSession().close();
            }
        }

        @Override
        public void afterCompletion(int status) {
            if (this.holderActive) {
                TransactionSynchronizationManager.unbindResourceIfPossible(sessionHolder);
                this.holderActive = false;
                this.holder.getSession().close();
            }
            this.holder.reset();
        }
    }

    static class SessionHolderSupport extends ResourceHolderSupport {

        private final Session session;

        public SessionHolderSupport(Session session) {
            this.session = session;
        }

        public Session getSession() {
            return session;
        }

    }
}
