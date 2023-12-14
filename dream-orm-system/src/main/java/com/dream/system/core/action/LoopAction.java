package com.dream.system.core.action;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;

public interface LoopAction {
    /**
     * 查询结果循环遍历行为
     *
     * @param row             如果查询结果是集合，则row集合的单个元素，否则是结果本身
     * @param mappedStatement 编译后的接口方法详尽信息
     * @param session         SQL操作会话
     */
    void loop(Object row, MappedStatement mappedStatement, Session session);
}
