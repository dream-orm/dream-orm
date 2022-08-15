package com.moxa.dream.driver.dialect;

public class MSSQLDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.MSSQL;
    }
}
