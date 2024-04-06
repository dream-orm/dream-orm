package com.dream.stream.wrapper;

import com.dream.antlr.smt.HavingStatement;
import com.dream.antlr.smt.Statement;

import java.util.function.Consumer;

public interface HavingWrapper<T,
        OrderBy extends OrderByWrapper<T, Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<T, Union, ForUpdate, Query>,
        Union extends UnionWrapper<T, ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<T, Query>,
        Query extends QueryWrapper<T>> extends OrderByWrapper<T, Limit, Union, ForUpdate, Query> {
    default OrderBy having(Consumer<ConditionWrapper.StatementConditionWrapper> fn) {
        ConditionWrapper.StatementConditionWrapper conditionWrapper = new ConditionWrapper.StatementConditionWrapper();
        fn.accept(conditionWrapper);
        Statement statement = conditionWrapper.statement();
        HavingStatement havingStatement = statement().getHavingStatement();
        if (havingStatement == null) {
            havingStatement = new HavingStatement();
            statement().setHavingStatement(havingStatement);
        }
        havingStatement.setCondition(statement);
        return (OrderBy) creatorFactory().newOrderByWrapper(entityType(), statement());
    }
}
