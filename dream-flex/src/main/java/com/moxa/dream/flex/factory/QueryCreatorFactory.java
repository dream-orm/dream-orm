package com.moxa.dream.flex.factory;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.*;

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

    Select newSelectDef(QueryStatement statement);

    From newFromDef(QueryStatement statement);

    Where newWhereDef(QueryStatement statement);

    GroupBy newGroupByDef(QueryStatement statement);

    Having newHavingDef(QueryStatement statement);

    OrderBy newOrderByDef(QueryStatement statement);

    Limit newLimitDef(QueryStatement statement);

    Union newUnionDef(QueryStatement statement);

    ForUpdate newForUpdateDef(QueryStatement statement);
}
