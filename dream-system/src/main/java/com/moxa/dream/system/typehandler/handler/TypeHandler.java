package com.moxa.dream.system.typehandler.handler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 类型转换器
 *
 * @param <T>
 */
public interface TypeHandler<T> {

    /**
     * 设置SQL执行占位符参数
     *
     * @param ps        预编译处理类
     * @param index     位置
     * @param parameter 参数值
     * @param jdbcType  数据库表字段类型
     * @throws SQLException
     */
    void setParam(PreparedStatement ps, int index, T parameter, int jdbcType) throws SQLException;

    /**
     * 获取数据库表字段值
     *
     * @param rs       结果迭代器
     * @param index    位置
     * @param jdbcType 数据库表字段类型
     * @return
     * @throws SQLException
     */
    T getResult(ResultSet rs, int index, int jdbcType) throws SQLException;

    /**
     * 获取数据库表字段值
     *
     * @param rs       结果迭代器
     * @param column   数据库表字段名
     * @param jdbcType 数据库表字段类型
     * @return
     * @throws SQLException
     */
    T getResult(ResultSet rs, String column, int jdbcType) throws SQLException;
}
