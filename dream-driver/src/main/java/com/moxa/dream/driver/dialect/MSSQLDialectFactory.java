package com.moxa.dream.driver.dialect;

import com.moxa.dream.antlr.sql.ToMSSQL;
import com.moxa.dream.antlr.sql.ToSQL;

public class MSSQLDialectFactory extends AbstractDialectFactory {
    @Override
    protected ToSQL getToSQL() {
        return new ToMSSQL();
    }
}
