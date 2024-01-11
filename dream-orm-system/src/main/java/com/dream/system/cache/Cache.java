package com.dream.system.cache;

import com.dream.system.config.MappedStatement;

/**
 * 缓存接口
 */
public interface Cache {
    /**
     * 是否开启缓存
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @return 是否开启缓存
     */
    default boolean cache(MappedStatement mappedStatement) {
        return true;
    }

    /**
     * 缓存数据
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @param value           数据库查询后的值
     */
    void put(MappedStatement mappedStatement, Object value);

    /**
     * 获取数据
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     * @return
     */
    Object get(MappedStatement mappedStatement);

    /**
     * 删除数据
     *
     * @param mappedStatement 编译后的接口方法详尽信息
     */
    void remove(MappedStatement mappedStatement);

    /**
     * 清空数据
     */
    void clear();
}
