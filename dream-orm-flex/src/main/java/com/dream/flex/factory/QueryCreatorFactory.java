package com.dream.flex.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.*;

public interface QueryCreatorFactory
        <Query extends QueryDef<Select, From, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate>,
                Select extends SelectDef<From, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate>,
                From extends FromDef<From, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate>,
                Where extends WhereDef<GroupBy, Having, OrderBy, Limit, Union, ForUpdate>,
                GroupBy extends GroupByDef<Having, OrderBy, Limit, Union, ForUpdate>,
                Having extends HavingDef<OrderBy, Limit, Union, ForUpdate>,
                OrderBy extends OrderByDef<Limit, Union, ForUpdate>,
                Limit extends LimitDef<Union, ForUpdate>,
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
