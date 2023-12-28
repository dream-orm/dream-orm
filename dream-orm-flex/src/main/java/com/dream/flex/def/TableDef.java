package com.dream.flex.def;

import com.dream.antlr.smt.*;
import com.dream.flex.invoker.FlexTableSymbolStatement;

public class TableDef {
    private AliasStatement statement;
    private ListColumnStatement joinList;

    public TableDef(String table) {
        this(table, null);
    }

    public TableDef(String table, String alias) {
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(new FlexTableSymbolStatement(table));
        this.statement = aliasStatement;
        if (alias != null && !alias.isEmpty()) {
            this.as(alias);
        }
    }

    protected TableDef(AliasStatement statement) {
        this(statement, null);
    }

    protected TableDef(AliasStatement statement, ListColumnStatement joinList) {
        this.statement = statement;
        this.joinList = joinList;
    }

    public AliasStatement getStatement() {
        return statement;
    }

    public ListColumnStatement getJoinList() {
        return joinList;
    }

    protected TableDef as(String alias) {
        statement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
        return this;
    }

    public JoinOnDef leftJoin(TableDef tableDef) {
        return new JoinOnDef(this, new JoinStatement.LeftJoinStatement(), tableDef.getStatement());
    }

    public JoinOnDef rightJoin(TableDef tableDef) {
        return new JoinOnDef(this, new JoinStatement.RightJoinStatement(), tableDef.getStatement());
    }

    public JoinOnDef innerJoin(TableDef tableDef) {
        return new JoinOnDef(this, new JoinStatement.InnerJoinStatement(), tableDef.getStatement());
    }

    public TableDef leftJoin(TableDef tableDef, ColumnDef columnDef, ColumnDef columnDef2) {
        return join(new JoinStatement.LeftJoinStatement(), tableDef.getStatement(), columnDef.getStatement(), columnDef2.getStatement());
    }

    public TableDef rightJoin(TableDef tableDef, ColumnDef columnDef, ColumnDef columnDef2) {
        return join(new JoinStatement.RightJoinStatement(), tableDef.getStatement(), columnDef.getStatement(), columnDef2.getStatement());
    }

    public TableDef innerJoin(TableDef tableDef, ColumnDef columnDef, ColumnDef columnDef2) {
        return join(new JoinStatement.InnerJoinStatement(), tableDef.getStatement(), columnDef.getStatement(), columnDef2.getStatement());
    }

    protected TableDef join(JoinStatement joinStatement, Statement joinTable, Statement leftColumn, Statement rightColumn) {
        joinStatement.setJoinTable(joinTable);
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(leftColumn);
        conditionStatement.setOper(new OperStatement.EQStatement());
        conditionStatement.setRight(rightColumn);
        return join(joinStatement, joinTable, conditionStatement);
    }

    protected TableDef join(JoinStatement joinStatement, Statement joinTable, ConditionStatement conditionStatement) {
        joinStatement.setJoinTable(joinTable);
        joinStatement.setOn(conditionStatement);
        if (joinList == null) {
            ListColumnStatement joinList = new ListColumnStatement(" ");
            joinList.add(joinStatement);
            return new TableDef(statement, joinList);

        } else {
            joinList.add(joinStatement);
            return this;
        }
    }

    public static class JoinOnDef {
        private TableDef tableDef;
        private JoinStatement joinStatement;
        private Statement joinTable;

        protected JoinOnDef(TableDef tableDef, JoinStatement joinStatement, Statement joinTable) {
            this.tableDef = tableDef;
            this.joinStatement = joinStatement;
            this.joinTable = joinTable;
        }

        public TableDef on(ConditionDef conditionDef) {
            return tableDef.join(joinStatement, joinTable, conditionDef.getStatement());
        }
    }
}
