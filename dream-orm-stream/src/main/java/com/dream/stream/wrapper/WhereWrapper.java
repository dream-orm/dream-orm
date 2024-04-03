package com.dream.stream.wrapper;

import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.WhereStatement;

import java.util.function.Consumer;

public interface WhereWrapper<
        GroupBy extends GroupByWrapper<Having, OrderBy, Limit, Union, ForUpdate, Query>,
        Having extends HavingWrapper<OrderBy, Limit, Union, ForUpdate, Query>,
        OrderBy extends OrderByWrapper<Limit, Union, ForUpdate, Query>,
        Limit extends LimitWrapper<Union, ForUpdate, Query>,
        Union extends UnionWrapper<ForUpdate, Query>,
        ForUpdate extends ForUpdateWrapper<Query>,
        Query extends QueryWrapper> extends GroupByWrapper<Having, OrderBy, Limit, Union, ForUpdate, Query> {
    default GroupBy where(Consumer<ConditionWrapper.StatementConditionWrapper> fn) {
        ConditionWrapper.StatementConditionWrapper conditionWrapper = new ConditionWrapper.StatementConditionWrapper();
        fn.accept(conditionWrapper);
        Statement statement = conditionWrapper.statement();
        WhereStatement whereStatement = statement().getWhereStatement();
        if (whereStatement == null) {
            whereStatement = new WhereStatement();
            statement().setWhereStatement(whereStatement);
        }
        whereStatement.setStatement(statement);
        return (GroupBy) creatorFactory().newGroupByWrapper(statement());
    }
}
