package com.moxa.dream.module.table.factory;


import com.moxa.dream.module.table.TableInfo;

public interface TableFactory {
    void addTableInfo(Class type);

    TableInfo getTableInfo(String table);

}
