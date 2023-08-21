package com.dream.flex.def;

import com.dream.antlr.smt.ConditionStatement;
import com.dream.antlr.smt.WhereStatement;

public interface WhereDef<
        Group extends GroupByDef<Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Having extends HavingDef<OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByDef<Limit, Union, ForUpdate, Query>,
        Limit extends LimitDef<Union, ForUpdate, Query>,
        Union extends UnionDef<ForUpdate, Query>,
        ForUpdate extends ForUpdateDef<Query>,
        Query extends QueryDef>
        extends GroupByDef<Having, OrderBy, Limit, Union, ForUpdate, Query> {

    default Group where(ConditionDef conditionDef) {
        ConditionStatement conditionStatement = conditionDef.getStatement();
        if (conditionStatement != null) {
            WhereStatement whereStatement = new WhereStatement();
            whereStatement.setCondition(conditionDef.getStatement());
            statement().setWhereStatement(whereStatement);
        }
        return (Group) creatorFactory().newGroupByDef(statement());
    }
}
