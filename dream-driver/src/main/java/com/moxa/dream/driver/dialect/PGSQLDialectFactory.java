package com.moxa.dream.driver.dialect;

public class PGSQLDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.PGSQL;
    }
}
