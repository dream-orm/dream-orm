package com.moxa.dream.drive.dialect;

public class MySQLDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.MYSQL;
    }
}
