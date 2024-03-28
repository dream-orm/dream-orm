package com.dream.lambda.wrapper.defaults;

import com.dream.antlr.smt.ConditionStatement;
import com.dream.antlr.smt.OperStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.WhereStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.lambda.factory.QueryCreatorFactory;
import com.dream.lambda.wrapper.ConditionWrapper;
import com.dream.lambda.wrapper.SelectWrapper;

public class DefaultSelectWrapper extends ConditionWrapper<DefaultSelectWrapper> implements SelectWrapper<DefaultFromWrapper, DefaultWhereWrapper, DefaultGroupByWrapper, DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    private QueryStatement statement = new QueryStatement();
    private QueryCreatorFactory creatorFactory;

    public DefaultSelectWrapper(QueryCreatorFactory creatorFactory) {
        this.creatorFactory = creatorFactory;
    }

    @Override
    public QueryStatement statement() {
        return statement;
    }

    @Override
    public QueryCreatorFactory creatorFactory() {
        return creatorFactory;
    }

    @Override
    protected void accept(ConditionStatement statement) {
        WhereStatement whereStatement = this.statement.getWhereStatement();
        if (whereStatement == null) {
            whereStatement = new WhereStatement();
            whereStatement.setStatement(statement);
            this.statement.setWhereStatement(whereStatement);
            return;
        }
        ConditionStatement conditionStatement = AntlrUtil.conditionStatement(whereStatement.getStatement(), new OperStatement.ANDStatement(), statement);
        whereStatement.setStatement(conditionStatement);
    }
}
