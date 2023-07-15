package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.config.SqlInfo;


public interface Query {
    QueryStatement getStatement();

    SqlInfo toSQL(ToSQL toSQL);
}
