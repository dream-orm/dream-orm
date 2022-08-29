package com.moxa.dream.drive.dialect;

public class MSSQLDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.MSSQL;
    }
}
