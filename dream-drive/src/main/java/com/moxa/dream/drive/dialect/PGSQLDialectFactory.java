package com.moxa.dream.drive.dialect;

public class PGSQLDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.PGSQL;
    }
}
