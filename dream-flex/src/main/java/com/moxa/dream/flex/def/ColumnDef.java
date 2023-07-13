package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.flex.function.LazyFunctionStatement;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class ColumnDef {
    private TableDef tableDef;
    private Statement statement;
    private String alias;

    public ColumnDef(Statement statement) {
        this.statement = statement;
    }

    public ColumnDef(TableDef tableDef, String column, String alias) {
        this(new SymbolStatement.SingleMarkStatement(column));
        this.tableDef = tableDef;
        if (!column.equals(alias)) {
            this.alias = alias;
        }
    }

    public static ColumnDef column(Serializable column) {
        return new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(column)));
    }

    public Statement getStatement() {
        return getStatement(false);
    }

    public Statement getStatement(boolean hasAlias) {
        Statement tempStatement = statement;
        if (tableDef != null) {
            ListColumnStatement listColumnStatement = new ListColumnStatement(".");
            listColumnStatement.add(new LazyFunctionStatement(() -> {
                AliasStatement tableStatement = tableDef.getStatement();
                Statement aliasStatement = tableStatement.getAlias();
                if (aliasStatement == null) {
                    aliasStatement = tableStatement.getColumn();
                }
                return aliasStatement;
            }));
            listColumnStatement.add(statement);
            tempStatement = listColumnStatement;
        }
        if (hasAlias && alias != null && !alias.isEmpty()) {
            AliasStatement aliasStatement = new AliasStatement();
            aliasStatement.setColumn(tempStatement);
            aliasStatement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
            tempStatement = aliasStatement;
        }
        return tempStatement;
    }

    public ColumnDef as(String alias) {
        this.alias = alias;
        return this;
    }

    public ConditionDef eq(ColumnDef columnDef) {
        return conditionDef(new OperStatement.EQStatement(), columnDef.getStatement());
    }

    public ConditionDef eq(Serializable value) {
        return conditionDef(new OperStatement.EQStatement(), value);
    }

    public ConditionDef neq(ColumnDef columnDef) {
        return conditionDef(new OperStatement.NEQStatement(), columnDef.getStatement());
    }

    public ConditionDef neq(Serializable value) {
        return conditionDef(new OperStatement.NEQStatement(), value);
    }

    public ConditionDef leq(ColumnDef columnDef) {
        return conditionDef(new OperStatement.LEQStatement(), columnDef.getStatement());
    }

    public ConditionDef leq(Serializable value) {
        return conditionDef(new OperStatement.LEQStatement(), value);
    }

    public ConditionDef lt(ColumnDef columnDef) {
        return conditionDef(new OperStatement.LTStatement(), columnDef.getStatement());
    }

    public ConditionDef lt(Serializable value) {
        return conditionDef(new OperStatement.LTStatement(), value);
    }

    public ConditionDef geq(ColumnDef columnDef) {
        return conditionDef(new OperStatement.GEQStatement(), columnDef.getStatement());
    }

    public ConditionDef geq(Serializable value) {
        return conditionDef(new OperStatement.GEQStatement(), value);
    }

    public ConditionDef gt(ColumnDef columnDef) {
        return conditionDef(new OperStatement.GTStatement(), columnDef.getStatement());
    }

    public ConditionDef gt(Serializable value) {
        return conditionDef(new OperStatement.GTStatement(), value);
    }

    public ConditionDef in(Serializable... values) {
        return in(Arrays.asList(values));
    }

    public ConditionDef in(Collection<?> values) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        Iterator<?> iterator = values.iterator();
        while (iterator.hasNext()) {
            Object value = iterator.next();
            listColumnStatement.add(new FlexMarkInvokerStatement(value));
        }
        BraceStatement braceStatement = new BraceStatement(listColumnStatement);
        return conditionDef(new OperStatement.INStatement(), braceStatement);
    }

    public ConditionDef in(SqlDef sqlDef) {
        BraceStatement braceStatement = new BraceStatement(sqlDef.getStatement());
        return conditionDef(new OperStatement.INStatement(), braceStatement);
    }

    public ConditionDef notIn(Serializable... values) {
        return not(in(Arrays.asList(values)));
    }

    public ConditionDef notIn(Collection<?> values) {
        return not(in(values));
    }

    public ConditionDef notIn(SqlDef sqlDef) {
        return not(in(sqlDef));
    }

    public ConditionDef like(Serializable value) {
        return conditionDef(new OperStatement.LIKEStatement(), "%" + value + "%");
    }

    public ConditionDef likeLeft(Serializable value) {
        return conditionDef(new OperStatement.LIKEStatement(), "%" + value);
    }

    public ConditionDef likeRight(Serializable value) {
        return conditionDef(new OperStatement.LIKEStatement(), value + "%");
    }

    public ConditionDef notLike(Serializable value) {
        return not(like(value));
    }

    public ConditionDef notLikeLeft(Serializable value) {
        return not(likeLeft(value));
    }

    public ConditionDef notLikeRight(Serializable value) {
        return not(likeRight(value));
    }

    public ConditionDef between(Serializable start, Serializable end) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(this.getStatement());
        conditionStatement.setOper(new OperStatement.BETWEENStatement());
        ConditionStatement rightConditionStatement = new ConditionStatement();
        rightConditionStatement.setLeft(new SymbolStatement.LetterStatement(String.valueOf(start)));
        rightConditionStatement.setOper(new OperStatement.ANDStatement());
        rightConditionStatement.setRight(new SymbolStatement.LetterStatement(String.valueOf(end)));
        conditionStatement.setRight(rightConditionStatement);
        return new ConditionDef(conditionStatement);
    }

    public ConditionDef notBetween(Serializable start, Serializable end) {
        return not(between(start, end));
    }

    public ConditionDef isNull() {
        return conditionDef(new OperStatement.ISStatement(), new SymbolStatement.LetterStatement("NULL"));
    }

    public ConditionDef isNotNull() {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(this.statement);
        conditionStatement.setOper(new OperStatement.ISStatement());
        ConditionStatement rightConditionStatement = new ConditionStatement();
        rightConditionStatement.setOper(new OperStatement.NOTStatement());
        rightConditionStatement.setRight(new SymbolStatement.LetterStatement("NULL"));
        conditionStatement.setRight(rightConditionStatement);
        return new ConditionDef(conditionStatement);
    }

    private ConditionDef not(ConditionDef conditionDef) {
        ConditionStatement statement = conditionDef.statement;
        Statement left = statement.getLeft();
        statement.setLeft(null);
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(left);
        conditionStatement.setOper(new OperStatement.NOTStatement());
        conditionStatement.setRight(statement);
        return new ConditionDef(conditionStatement);
    }

    public ColumnDef add(ColumnDef columnDef) {
        return new ColumnDef(conditionDef(new OperStatement.ADDStatement(), columnDef.getStatement()).statement);
    }

    public ColumnDef add(Number number) {
        return add(new ColumnDef(new SymbolStatement.LetterStatement(number.toString())));
    }

    public ColumnDef sub(ColumnDef columnDef) {
        return new ColumnDef(conditionDef(new OperStatement.SUBStatement(), columnDef.getStatement()).statement);
    }

    public ColumnDef sub(Number number) {
        return sub(new ColumnDef(new SymbolStatement.LetterStatement(number.toString())));
    }

    public ColumnDef multiply(ColumnDef columnDef) {
        return new ColumnDef(conditionDef(new OperStatement.STARStatement(), columnDef.getStatement()).statement);
    }

    public ColumnDef multiply(Number number) {
        return multiply(new ColumnDef(new SymbolStatement.LetterStatement(number.toString())));
    }

    public ColumnDef divide(ColumnDef columnDef) {
        return new ColumnDef(conditionDef(new OperStatement.DIVIDEStatement(), columnDef.getStatement()).statement);
    }

    public ColumnDef divide(Number number) {
        return divide(new ColumnDef(new SymbolStatement.LetterStatement(number.toString())));
    }

    protected ConditionDef conditionDef(OperStatement operStatement, Serializable value) {
        return conditionDef(operStatement, new FlexMarkInvokerStatement(value));
    }

    protected ConditionDef conditionDef(OperStatement operStatement, Statement statement) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(this.getStatement());
        conditionStatement.setOper(operStatement);
        conditionStatement.setRight(statement);
        return new ConditionDef(conditionStatement);
    }

    public SortDef asc() {
        OrderStatement.AscStatement ascStatement = new OrderStatement.AscStatement(this.getStatement());
        return new SortDef(ascStatement);
    }

    public SortDef desc() {
        OrderStatement.DescStatement descStatement = new OrderStatement.DescStatement(this.getStatement());
        return new SortDef(descStatement);
    }

}
