package com.moxa.dream.drive.dialect;

public class ORACLEDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.ORACLE;
    }
}
