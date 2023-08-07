package com.dream.flex.def;

import com.dream.antlr.smt.FromStatement;

public interface SelectDef<T extends FromDef> extends Query {
    default T from(TableDef tableDef) {
        FromStatement fromStatement = new FromStatement();
        fromStatement.setMainTable(tableDef.getStatement());
        statement().setFromStatement(fromStatement);
        return (T) creatorFactory().newFromDef(statement());
    }
}
