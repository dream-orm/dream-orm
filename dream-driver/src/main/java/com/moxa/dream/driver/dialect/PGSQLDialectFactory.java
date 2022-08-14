package com.moxa.dream.driver.dialect;

import com.moxa.dream.antlr.sql.ToPGSQL;
import com.moxa.dream.antlr.sql.ToSQL;

public class PGSQLDialectFactory extends AbstractDialectFactory {
    @Override
    protected ToSQL getToSQL() {
        return new ToPGSQL();
    }
}
