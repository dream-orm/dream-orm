package com.moxa.dream.driver.dialect;

public class MySQLDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.MYSQL;
    }
}
