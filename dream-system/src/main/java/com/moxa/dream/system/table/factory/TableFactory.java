package com.moxa.dream.system.table.factory;


import com.moxa.dream.system.table.TableInfo;

public interface TableFactory {
    void addTableInfo(Class type);

    TableInfo getTableInfo(String table);

}
