package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.FromStatement;

public interface SelectDef<T extends FromDef> extends Query {
    default T from(TableDef tableDef) {
        FromStatement fromStatement = new FromStatement();
        fromStatement.setMainTable(tableDef.getStatement());
        statement().setFromStatement(fromStatement);
        return (T) queryCreatorFactory().newFromDef(statement());
    }
}
