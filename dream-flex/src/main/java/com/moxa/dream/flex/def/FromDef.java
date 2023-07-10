package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.*;

public class FromDef extends WhereDef {

    private ListColumnStatement listColumnStatement = new ListColumnStatement(" ");

    protected FromDef(QueryStatement statement) {
        super(statement);
    }

    public JoinDef leftJoin(TableDef tableDef) {
        return join(new JoinStatement.LeftJoinStatement(), tableDef.getStatement());
    }

    public JoinDef rightJoin(TableDef tableDef) {
        return join(new JoinStatement.RightJoinStatement(), tableDef.getStatement());
    }

    public JoinDef innerJoin(TableDef tableDef) {
        return join(new JoinStatement.InnerJoinStatement(), tableDef.getStatement());
    }

    public FromDef crossJoin(TableDef tableDef) {
        JoinStatement.CrossJoinStatement crossJoinStatement = new JoinStatement.CrossJoinStatement();
        crossJoinStatement.setJoinTable(tableDef.getStatement());
        listColumnStatement.add(crossJoinStatement);
        FromStatement fromStatement = statement.getFromStatement();
        fromStatement.setJoinList(listColumnStatement);
        return this;
    }

    private JoinDef join(JoinStatement joinStatement, Statement joinTableStatement) {
        joinStatement.setJoinTable(joinTableStatement);
        FromStatement fromStatement = statement.getFromStatement();
        listColumnStatement.add(joinStatement);
        fromStatement.setJoinList(listColumnStatement);
        return new JoinDef(this, joinStatement);
    }

    public WhereDef where(ConditionDef conditionDef) {
        WhereStatement whereStatement = new WhereStatement();
        whereStatement.setCondition(conditionDef.statement);
        statement.setWhereStatement(whereStatement);
        return new WhereDef(statement);
    }
}
