package com.dream.wrap.wrapper;

public interface WhereWrapper<
        GroupBy extends GroupByWrapper<Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Having extends HavingWrapper<OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByWrapper<Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<Union, ForUpdate, Query>,
        Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends GroupByWrapper<Having, OrderBy, Limit, Union, ForUpdate, Query> {
}