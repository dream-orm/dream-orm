package com.dream.lambda.wrapper;

public interface FromWrapper<
        Where extends WhereWrapper<Group, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Group extends GroupByWrapper<Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Having extends HavingWrapper<OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByWrapper<Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<Union, ForUpdate, Query>,
        Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper>
        extends QueryWrapper, WhereWrapper<Group, Having, OrderBy, Limit, Union, ForUpdate, Query> {

}
