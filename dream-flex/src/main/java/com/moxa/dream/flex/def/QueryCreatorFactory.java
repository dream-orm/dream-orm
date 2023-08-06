package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.QueryStatement;

public interface QueryCreatorFactory
        <Query extends QueryDef<Select>,
                Select extends SelectDef<From>,
                From extends FromDef<From, Where>,
                Where extends WhereDef<GroupBy>,
                GroupBy extends GroupByDef<Having>,
                Having extends HavingDef<OrderBy>,
                OrderBy extends OrderByDef<Limit>,
                Limit extends LimitDef<Union>,
                Union extends UnionDef<ForUpdate>,
                ForUpdate extends ForUpdateDef> {

    Query newQueryDef();

    Select newSelectDef(QueryStatement queryStatement);

    From newFromDef(QueryStatement queryStatement);

    Where newWhereDef(QueryStatement queryStatement);

    GroupBy newGroupByDef(QueryStatement queryStatement);

    Having newHavingDef(QueryStatement queryStatement);

    OrderBy newOrderByDef(QueryStatement queryStatement);

    Limit newLimitDef(QueryStatement queryStatement);

    Union newUnionDef(QueryStatement queryStatement);

    ForUpdate newForUpdateDef(QueryStatement queryStatement);
}
