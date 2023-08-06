package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.antlr.smt.WhereStatement;


public class DeleteTableDef {
    private DeleteStatement statement;

    protected DeleteTableDef(DeleteStatement statement) {
        this.statement = statement;
    }

    public DeleteWhereDef where(ConditionDef conditionDef) {
        WhereStatement whereStatement = new WhereStatement();
        whereStatement.setCondition(conditionDef.getStatement());
        statement.setWhere(whereStatement);
        return new DeleteWhereDef(statement);
    }
}
