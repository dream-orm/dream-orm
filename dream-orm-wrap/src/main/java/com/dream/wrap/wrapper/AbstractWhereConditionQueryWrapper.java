package com.dream.wrap.wrapper;

import com.dream.antlr.smt.OperStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.WhereStatement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.wrap.factory.WrapQueryFactory;

public class AbstractWhereConditionQueryWrapper<Children extends ConditionWrapper<Children>> extends ConditionWrapper<Children> implements QueryWrapper {
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
