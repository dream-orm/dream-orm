package com.dream.system.core.resultsethandler;

import com.dream.system.config.MappedStatement;

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
     * @return 查询结果集
     * @throws SQLException
     */
    Object result(ResultSet resultSet, MappedStatement mappedStatement) throws SQLException;
}
