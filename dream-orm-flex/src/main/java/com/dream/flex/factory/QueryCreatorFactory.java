package com.dream.flex.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.*;

public interface QueryCreatorFactory
        <Select extends SelectDef<From, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                From extends FromDef<Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                Where extends WhereDef<GroupBy, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                GroupBy extends GroupByDef<Having, OrderBy, Limit, Union, ForUpdate, Query>,
                Having extends HavingDef<OrderBy, Limit, Union, ForUpdate, Query>,
                OrderBy extends OrderByDef<Limit, Union, ForUpdate, Query>,
                Limit extends LimitDef<Union, ForUpdate, Query>,
                Union extends UnionDef<ForUpdate, Query>,
                ForUpdate extends ForUpdateDef<Query>,
                Query extends QueryDef> {

    Select newSelectDef();

    From newFromDef(QueryStatement statement);

    Where newWhereDef(QueryStatement statement);

    GroupBy newGroupByDef(QueryStatement statement);

    Having newHavingDef(QueryStatement statement);

    OrderBy newOrderByDef(QueryStatement statement);

    Limit newLimitDef(QueryStatement statement);

    Union newUnionDef(QueryStatement statement);

    ForUpdate newForUpdateDef(QueryStatement statement);

    Query newQueryDef(QueryStatement statement);

}
