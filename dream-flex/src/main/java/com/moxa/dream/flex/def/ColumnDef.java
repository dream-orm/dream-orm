package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.flex.invoker.FlexValueInvokerStatement;

public class ColumnDef {
    protected Statement statement;

    public ColumnDef(Statement statement) {
        this.statement = statement;
    }

    public ColumnDef(String column) {
        this(null, column);
    }

    public ColumnDef(String table, String column) {
        this(table, column, null);
    }

    public ColumnDef(String table, String column, String alias) {
        Statement columnStatement = new SymbolStatement.SingleMarkStatement(column);
        if (table != null) {
            ListColumnStatement listColumnStatement = new ListColumnStatement(".");
            listColumnStatement.add(new SymbolStatement.SingleMarkStatement(table));
            listColumnStatement.add(columnStatement);
            columnStatement = listColumnStatement;
        }
        if (alias != null) {
            AliasStatement aliasStatement = new AliasStatement();
            aliasStatement.setColumn(columnStatement);
            aliasStatement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
            columnStatement = aliasStatement;
        }
        statement = columnStatement;
    }

    public static ColumnDef column(String column) {
        return new ColumnDef(new SymbolStatement.LetterStatement(column));
    }

    public ColumnDef as(String alias) {
        SymbolStatement.SingleMarkStatement aliasSymbolStatement = new SymbolStatement.SingleMarkStatement(alias);
        if (this.statement instanceof AliasStatement) {
            ((AliasStatement) this.statement).setAlias(aliasSymbolStatement);
        } else {
            AliasStatement aliasStatement = new AliasStatement();
            aliasStatement.setColumn(statement);
            aliasStatement.setAlias(aliasSymbolStatement);
            this.statement = aliasStatement;
        }
        return this;
    }

    public ConditionDef eq(Object value) {
        return conditionDef(new OperStatement.EQStatement(), value);
    }

    public ConditionDef neq(Object value) {
        return conditionDef(new OperStatement.NEQStatement(), value);
    }

    public ConditionDef like(Object value) {
        return conditionDef(new OperStatement.LIKEStatement(), "%" + value + "%");
    }

    public ConditionDef likeLeft(Object value) {
        return conditionDef(new OperStatement.LIKEStatement(), "%" + value);
    }

    public ConditionDef likeRight(Object value) {
        return conditionDef(new OperStatement.LIKEStatement(), value + "%");
    }

    protected ConditionDef conditionDef(OperStatement operStatement, Object value) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(statement);
        conditionStatement.setOper(operStatement);
        conditionStatement.setRight(new FlexValueInvokerStatement(value));
        return new ConditionDef(conditionStatement);
    }

    public SortDef asc() {
        OrderStatement.AscStatement ascStatement = new OrderStatement.AscStatement(statement);
        return new SortDef(ascStatement);
    }

    public SortDef desc() {
        OrderStatement.DescStatement descStatement = new OrderStatement.DescStatement(statement);
        return new SortDef(descStatement);
    }
}
