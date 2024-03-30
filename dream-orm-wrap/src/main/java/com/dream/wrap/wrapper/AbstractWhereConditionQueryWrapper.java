package com.dream.wrap.wrapper;

import com.dream.antlr.smt.*;
import com.dream.antlr.util.AntlrUtil;
import com.dream.wrap.factory.WrapQueryFactory;

public class AbstractWhereConditionQueryWrapper<Children extends ConditionWrapper> extends ConditionWrapper<Children> implements QueryWrapper {
    private QueryStatement statement;
    private WrapQueryFactory creatorFactory;

    public AbstractWhereConditionQueryWrapper(QueryStatement statement, WrapQueryFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public QueryStatement statement() {
        return statement;
    }

    @Override
    public WrapQueryFactory creatorFactory() {
        return creatorFactory;
    }

    @Override
    protected Children condition(boolean or, Statement columnStatement, OperStatement operStatement, Statement valueStatement) {
        ConditionStatement conditionStatement = AntlrUtil.conditionStatement(columnStatement, operStatement, valueStatement);
        WhereStatement whereStatement = this.statement.getWhereStatement();
        if (whereStatement == null) {
            whereStatement = new WhereStatement();
            this.statement.setWhereStatement(whereStatement);
        } else if (or) {
            conditionStatement = AntlrUtil.conditionStatement(whereStatement.getStatement(), new OperStatement.ORStatement(), conditionStatement);
        } else {
            conditionStatement = AntlrUtil.conditionStatement(whereStatement.getStatement(), new OperStatement.ANDStatement(), conditionStatement);
        }
        whereStatement.setStatement(conditionStatement);
        return (Children) this;
    }
}
