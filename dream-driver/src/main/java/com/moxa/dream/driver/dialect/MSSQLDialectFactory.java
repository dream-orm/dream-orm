package com.moxa.dream.driver.dialect;

import com.moxa.dream.antlr.sql.ToMSSQL;
import com.moxa.dream.antlr.sql.ToSQL;

public class MSSQLDialectFactory extends AbstractPageDialectFactory {
    @Override
    protected ToSQL getToSQL() {
        return new ToMSSQL();
    }
}
