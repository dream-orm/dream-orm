package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.config.ResultInfo;

import java.util.Map;

public interface SqlDef {
    Statement getStatement();

    default ResultInfo toSQL(ToSQL toSQL, Invoker... invokers) {
        return toSQL(toSQL, null, invokers);
    }

    ResultInfo toSQL(ToSQL toSQL, Map<Class, Object> customMap, Invoker... invokers);
}
