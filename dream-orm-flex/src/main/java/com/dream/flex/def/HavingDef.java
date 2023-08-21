package com.dream.flex.def;

import com.dream.antlr.smt.ConditionStatement;
import com.dream.antlr.smt.HavingStatement;

public interface HavingDef<
        OrderBy extends OrderByDef<Limit, Union, ForUpdate, Query>,
        Limit extends LimitDef<Union, ForUpdate, Query>,
        Union extends UnionDef<ForUpdate, Query>,
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends OrderByDef<Limit, Union, ForUpdate, Query> {

    default OrderBy having(ConditionDef conditionDef) {
        ConditionStatement conditionStatement = conditionDef.getStatement();
        if (conditionStatement != null) {
            HavingStatement havingStatement = new HavingStatement();
            havingStatement.setCondition(conditionDef.getStatement());
            statement().setHavingStatement(havingStatement);
        }
        return (OrderBy) creatorFactory().newOrderByDef(statement());
    }
}
