package com.moxa.dream.system.table.factory;


import com.moxa.dream.system.table.TableInfo;

/**
 * java对象与数据库表映射创建类
 */
public interface TableFactory {
    /**
     * 新增java对象与数据库表映射处理
     *
     * @param type
     */
    void addTableInfo(Class type);

    /**
     * 根据数据库表名获取表详情
     *
     * @param table 数据库表
     * @return
     */
    TableInfo getTableInfo(String table);

}
