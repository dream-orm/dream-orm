package com.moxa.dream.driver.dialect;

import com.moxa.dream.antlr.sql.ToORACLE;
import com.moxa.dream.antlr.sql.ToSQL;

public class ORACLEDialectFactory extends AbstractDialectFactory {
    @Override
    protected ToSQL getToSQL() {
        return new ToORACLE();
    }
}
