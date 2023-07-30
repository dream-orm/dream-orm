package com.moxa.dream.system.core.resultsethandler;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL操作返回数据映射java对象
 */
public interface ResultSetHandler {

    /**
     * 返回映射后的java对象
     *
     * @param resultSet       SQL操作返回数据迭代器
     * @param mappedStatement 编译后的接口方法详尽信息
     * @param session         SQL操作会话
     * @return
     * @throws SQLException
     */
    Object result(ResultSet resultSet, MappedStatement mappedStatement, Session session) throws SQLException;
}
