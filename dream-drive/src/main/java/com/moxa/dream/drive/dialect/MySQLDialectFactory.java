package com.moxa.dream.drive.dialect;

import com.moxa.dream.system.dialect.DbType;

public class MySQLDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.MYSQL;
    }
}
