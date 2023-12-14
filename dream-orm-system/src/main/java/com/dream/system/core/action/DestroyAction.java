package com.dream.system.core.action;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;

public interface DestroyAction {
    /**
     * sql执行后行为
     *
     * @param result          查询结果
     * @param mappedStatement 编译后的接口方法详尽信息
     * @param session         SQL操作会话
     * @return 调用sql拿到的最终数据
     */
    Object destroy(Object result, MappedStatement mappedStatement, Session session);
}
