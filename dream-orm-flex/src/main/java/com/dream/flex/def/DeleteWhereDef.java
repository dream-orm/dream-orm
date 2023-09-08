package com.dream.flex.def;

import com.dream.antlr.smt.WhereStatement;

public interface DeleteWhereDef<T extends DeleteDef> extends DeleteDef {
    default T where(ConditionDef conditionDef) {
        WhereStatement whereStatement = new WhereStatement();
        whereStatement.setStatement(conditionDef.getStatement());
        statement().setWhere(whereStatement);
        return (T) creatorFactory().newDeleteDef(statement());
    }
}
