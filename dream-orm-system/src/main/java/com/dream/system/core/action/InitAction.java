package com.dream.system.core.action;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;

public interface InitAction {
    /**
     * sql执行前行为
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @param session         SQL操作会话
     */
    void init(MappedStatement mappedStatement, Session session);
}
