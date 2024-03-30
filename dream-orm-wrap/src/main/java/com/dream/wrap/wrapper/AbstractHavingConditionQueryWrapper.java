package com.dream.wrap.wrapper;

import com.dream.antlr.smt.*;
import com.dream.antlr.util.AntlrUtil;
import com.dream.wrap.factory.QueryCreatorFactory;

public class AbstractHavingConditionQueryWrapper<Children extends ConditionWrapper> extends ConditionWrapper<Children> implements QueryWrapper {
    private QueryStatement statement;
    private QueryCreatorFactory creatorFactory;

    public AbstractHavingConditionQueryWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        this.statement = statement;
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
    protected Children condition(boolean or, Statement columnStatement, OperStatement operStatement, Statement valueStatement) {
        ConditionStatement conditionStatement = AntlrUtil.conditionStatement(columnStatement, operStatement, valueStatement);
        HavingStatement havingStatement = this.statement.getHavingStatement();
        if (havingStatement == null) {
            havingStatement = new HavingStatement();
            this.statement.setHavingStatement(havingStatement);
        } else if (or) {
            conditionStatement = AntlrUtil.conditionStatement(havingStatement.getCondition(), new OperStatement.ORStatement(), conditionStatement);
        } else {
            conditionStatement = AntlrUtil.conditionStatement(havingStatement.getCondition(), new OperStatement.ANDStatement(), conditionStatement);
        }
        havingStatement.setCondition(conditionStatement);
        return (Children) this;
    }
}
