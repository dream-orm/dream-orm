package com.dream.stream.wrapper;

public interface FromWrapper<T,
        Where extends WhereWrapper<T, Group, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Group extends GroupByWrapper<T, Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Having extends HavingWrapper<T, OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByWrapper<T, Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<T, Union, ForUpdate, Query>,
        Union extends UnionWrapper<T, ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<T, Query>,
        Query extends QueryWrapper<T>>
        extends WhereWrapper<T, Group, Having, OrderBy, Limit, Union, ForUpdate, Query> {

}
