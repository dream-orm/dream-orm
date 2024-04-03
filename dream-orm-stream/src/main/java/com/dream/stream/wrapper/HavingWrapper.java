package com.dream.stream.wrapper;

import com.dream.antlr.smt.HavingStatement;
import com.dream.antlr.smt.Statement;

import java.util.function.Consumer;

public interface HavingWrapper<
        OrderBy extends OrderByWrapper<Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<Union, ForUpdate, Query>,
        Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends OrderByWrapper<Limit, Union, ForUpdate, Query> {
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
        return (OrderBy) creatorFactory().newOrderByWrapper(statement());
    }
}
