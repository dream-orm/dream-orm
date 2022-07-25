package com.moxa.dream.driver.dialect;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToSQL;

public class MySQLDialectFactory extends AbstractPageDialectFactory {
    @Override
    protected ToSQL getToSQL() {
        return new ToMYSQL();
    }
}
