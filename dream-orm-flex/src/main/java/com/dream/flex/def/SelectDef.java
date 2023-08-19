package com.dream.flex.def;

import com.dream.antlr.smt.FromStatement;

public interface SelectDef<From extends FromDef<From, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate>, Where extends WhereDef, GroupBy extends GroupByDef, Having extends HavingDef, OrderBy extends OrderByDef, Limit extends LimitDef, Union extends UnionDef, ForUpdate extends ForUpdateDef> extends Query {
    default From from(TableDef tableDef) {
        FromStatement fromStatement = new FromStatement();
        fromStatement.setMainTable(tableDef.getStatement());
        statement().setFromStatement(fromStatement);
        return (From) creatorFactory().newFromDef(statement());
    }
}
