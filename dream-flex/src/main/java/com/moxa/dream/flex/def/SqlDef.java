package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;

public interface SqlDef {
    Statement getStatement();

    ResultInfo toSQL(ToSQL toSQL);
}
