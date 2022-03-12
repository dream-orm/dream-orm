package com.moxa.dream.module.hold.table;


public interface TableFactory {

    void addTableInfo(Class type);

    TableInfo getTableInfo(String table);
}
