package com.moxa.dream.drive.dialect;

import com.moxa.dream.system.dialect.DbType;

public class PGSQLDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.PGSQL;
    }
}
