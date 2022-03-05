package com.moxa.dream.module.table;


public interface TableFactory {

    void addTableInfo(Class type);

    TableInfo getTableInfo(String table);
}
