package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.ConditionStatement;
import com.moxa.dream.antlr.smt.HavingStatement;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.WhereStatement;

public class GroupByDef extends HavingDef {

    protected GroupByDef(QueryStatement statement) {
        super(statement);
    }

    public HavingDef having(ConditionDef conditionDef) {
        ConditionStatement conditionStatement = conditionDef.getStatement();
        if (conditionStatement != null) {
            HavingStatement havingStatement = new HavingStatement();
            havingStatement.setCondition(conditionDef.getStatement());
            statement.setHavingStatement(havingStatement);
        }
        return new HavingDef(statement);
    }
}
