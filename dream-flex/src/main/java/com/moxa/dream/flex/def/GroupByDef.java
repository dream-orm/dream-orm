package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.ConditionStatement;
import com.moxa.dream.antlr.smt.HavingStatement;

public interface GroupByDef<T extends HavingDef> extends HavingDef {

    default T having(ConditionDef conditionDef) {
        ConditionStatement conditionStatement = conditionDef.getStatement();
        if (conditionStatement != null) {
            HavingStatement havingStatement = new HavingStatement();
            havingStatement.setCondition(conditionDef.getStatement());
            statement().setHavingStatement(havingStatement);
        }
        return (T) creatorFactory().newHavingDef(statement());
    }
}
