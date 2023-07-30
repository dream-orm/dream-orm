package com.moxa.dream.template.session;

import com.moxa.dream.system.core.session.Session;

/**
 * SQL操作会话获取类
 */
public interface SessionHolder {
    /**
     * 获取SQL操作会话
     *
     * @return
     */
    Session getSession();

    /**
     * 判断是否开启事务
     *
     * @param session
     * @return
     */
    boolean isSessionTransactional(Session session);

    /**
     * 关闭会话
     *
     * @param session
     */
    void closeSession(Session session);
}
