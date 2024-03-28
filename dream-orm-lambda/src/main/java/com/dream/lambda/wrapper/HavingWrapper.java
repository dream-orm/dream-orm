package com.dream.lambda.wrapper;

import java.util.function.Consumer;

public interface HavingWrapper<
        OrderBy extends OrderByWrapper<Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<Union, ForUpdate, Query>,
        Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends OrderByWrapper<Limit, Union, ForUpdate, Query> {

    OrderBy having(Consumer<ConditionWrapper> fn);
}
