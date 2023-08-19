package com.dream.flex.def;

import com.dream.antlr.smt.ConditionStatement;
import com.dream.antlr.smt.HavingStatement;

public interface GroupByDef<Having extends HavingDef, OrderBy extends OrderByDef, Limit extends LimitDef, Union extends UnionDef, ForUpdate extends ForUpdateDef> extends HavingDef<OrderBy, Limit, Union, ForUpdate> {

    default Having having(ConditionDef conditionDef) {
        ConditionStatement conditionStatement = conditionDef.getStatement();
        if (conditionStatement != null) {
            HavingStatement havingStatement = new HavingStatement();
            havingStatement.setCondition(conditionDef.getStatement());
            statement().setHavingStatement(havingStatement);
        }
        return (Having) creatorFactory().newHavingDef(statement());
    }
}
