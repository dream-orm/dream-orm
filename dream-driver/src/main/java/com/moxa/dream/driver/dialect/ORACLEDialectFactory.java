package com.moxa.dream.driver.dialect;

public class ORACLEDialectFactory extends AbstractDialectFactory {
    @Override
    public DbType getDbType() {
        return DbType.ORACLE;
    }
}
