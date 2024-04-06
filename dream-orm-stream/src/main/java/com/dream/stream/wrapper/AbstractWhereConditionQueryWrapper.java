package com.dream.stream.wrapper;

import com.dream.antlr.smt.OperStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.WhereStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.stream.factory.StreamQueryFactory;

public class AbstractWhereConditionQueryWrapper<T, Children extends ConditionWrapper<Children>> extends ConditionWrapper<Children> implements QueryWrapper<T> {
    private Class<T> entityType;
    private QueryStatement statement;
    private StreamQueryFactory creatorFactory;

    public AbstractWhereConditionQueryWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
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
        WhereStatement whereStatement = this.statement().getWhereStatement();
        if (whereStatement == null) {
            whereStatement = new WhereStatement();
            whereStatement.setStatement(valueStatement);
            this.statement().setWhereStatement(whereStatement);
        } else {
            whereStatement.setStatement(AntlrUtil.conditionStatement(whereStatement.getStatement(), operStatement, valueStatement));
        }
        return (Children) this;
    }
}
