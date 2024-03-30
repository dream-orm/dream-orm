package com.dream.wrap.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.wrapper.*;

public interface QueryCreatorFactory
        <Select extends SelectWrapper<From, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                From extends FromWrapper<Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                Where extends WhereWrapper<GroupBy, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                GroupBy extends GroupByWrapper<Having, OrderBy, Limit, Union, ForUpdate, Query>,
                Having extends HavingWrapper<OrderBy, Limit, Union, ForUpdate, Query>,
                OrderBy extends OrderByWrapper<Limit, Union, ForUpdate, Query>,
                Limit extends LimitWrapper<Union, ForUpdate, Query>,
                Union extends UnionWrapper<ForUpdate, Query>,
                ForUpdate extends ForUpdateWrapper<Query>,
                Query extends QueryWrapper> {

    Select newSelectWrapper(Class<?> entityType);

    From newFromWrapper(QueryStatement statement);

    Where newWhereWrapper(QueryStatement statement);

    GroupBy newGroupByWrapper(QueryStatement statement);

    Having newHavingWrapper(QueryStatement statement);

    OrderBy newOrderByWrapper(QueryStatement statement);

    Limit newLimitWrapper(QueryStatement statement);

    Union newUnionWrapper(QueryStatement statement);

    ForUpdate newForUpdateWrapper(QueryStatement statement);

    Query newQueryWrapper(QueryStatement statement);

}
