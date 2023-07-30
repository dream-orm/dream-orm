package com.moxa.dream.system.core.action;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;

public interface Action {

    /**
     * SQL操作执行的行为
     *
     * @param session         SQL操作会话
     * @param mappedStatement 编译后的接口方法详尽信息
     * @param arg             参数
     */
    void doAction(Session session, MappedStatement mappedStatement, Object arg);
}
