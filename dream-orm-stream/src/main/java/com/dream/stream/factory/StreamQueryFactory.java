package com.dream.stream.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.wrapper.*;

public interface StreamQueryFactory
        <T, Select extends SelectWrapper<T, From, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                From extends FromWrapper<T, Where, GroupBy, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                Where extends WhereWrapper<T, GroupBy, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                GroupBy extends GroupByWrapper<T, Having, OrderBy, Limit, Union, ForUpdate, Query>,
                Having extends HavingWrapper<T, OrderBy, Limit, Union, ForUpdate, Query>,
                OrderBy extends OrderByWrapper<T, Limit, Union, ForUpdate, Query>,
                Limit extends LimitWrapper<T, Union, ForUpdate, Query>,
                Union extends UnionWrapper<T, ForUpdate, Query>,
                ForUpdate extends ForUpdateWrapper<T, Query>,
                Query extends QueryWrapper<T>> {

    Select newSelectWrapper(Class<T> entityType);

    From newFromWrapper(Class<T> entityType, QueryStatement statement);

    Where newWhereWrapper(Class<T> entityType, QueryStatement statement);

    GroupBy newGroupByWrapper(Class<T> entityType, QueryStatement statement);

    Having newHavingWrapper(Class<T> entityType, QueryStatement statement);

    OrderBy newOrderByWrapper(Class<T> entityType, QueryStatement statement);

    Limit newLimitWrapper(Class<T> entityType, QueryStatement statement);

    Union newUnionWrapper(Class<T> entityType, QueryStatement statement);

    ForUpdate newForUpdateWrapper(Class<T> entityType, QueryStatement statement);

    Query newQueryWrapper(Class<T> entityType, QueryStatement statement);

}
