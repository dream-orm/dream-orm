package com.dream.stream.wrapper;

import com.dream.antlr.smt.HavingStatement;
import com.dream.antlr.smt.OperStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.stream.factory.StreamQueryFactory;

public class AbstractHavingConditionQueryWrapper<T, Children extends ConditionWrapper<Children>> extends ConditionWrapper<Children> implements QueryWrapper<T> {
    private Class<T> entityType;
    private QueryStatement statement;
    private StreamQueryFactory creatorFactory;

    public AbstractHavingConditionQueryWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        this.entityType = entityType;
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public Class<T> entityType() {
        return entityType;
    }

    @Override
    public QueryStatement statement() {
        return statement;
    }

    @Override
    public StreamQueryFactory creatorFactory() {
        return creatorFactory;
    }

    @Override
    protected Children condition(OperStatement operStatement, Statement valueStatement) {
        HavingStatement havingStatement = this.statement().getHavingStatement();
        if (havingStatement == null) {
            havingStatement = new HavingStatement();
            havingStatement.setCondition(valueStatement);
            this.statement().setHavingStatement(havingStatement);
        } else {
            havingStatement.setCondition(AntlrUtil.conditionStatement(havingStatement.getCondition(), operStatement, valueStatement));
        }
        return (Children) this;
    }
}
