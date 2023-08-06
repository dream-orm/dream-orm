package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.flex.function.LazyFunctionStatement;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

public class ColumnDef {
    private Statement statement;
    private String alias;

    public ColumnDef(Statement statement) {
        this.statement = statement;
    }

    public ColumnDef(TableDef tableDef, String column) {
        Statement statement = new SymbolStatement.SingleMarkStatement(column);
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
            statement = listColumnStatement;
        }
        this.statement = statement;
    }

    public Statement getStatement() {
        if (alias != null && !alias.isEmpty()) {
            AliasStatement aliasStatement = new AliasStatement();
            aliasStatement.setColumn(statement);
            aliasStatement.setAlias(new SymbolStatement.SingleMarkStatement(alias));
            return aliasStatement;
        } else {
            return statement;
        }
    }

    public ColumnDef as(String alias) {
        ColumnDef columnDef = new ColumnDef(statement);
        columnDef.alias = alias;
        return columnDef;
    }

    public ConditionDef eq(Object value) {
        return conditionDef(new OperStatement.EQStatement(), value);
    }

    public <T> ConditionDef eq(T value, Predicate<T> fn) {
        return eq(value).when(fn.test(value));
    }

    public ConditionDef neq(Object value) {
        return conditionDef(new OperStatement.NEQStatement(), value);
    }

    public <T> ConditionDef neq(T value, Predicate<T> fn) {
        return neq(value).when(fn.test(value));
    }

    public ConditionDef leq(Object value) {
        return conditionDef(new OperStatement.LEQStatement(), value);
    }

    public <T> ConditionDef leq(T value, Predicate<T> fn) {
        return leq(value).when(fn.test(value));
    }

    public ConditionDef lt(Object value) {
        return conditionDef(new OperStatement.LTStatement(), value);
    }

    public <T> ConditionDef lt(T value, Predicate<T> fn) {
        return lt(value).when(fn.test(value));
    }

    public ConditionDef geq(Object value) {
        return conditionDef(new OperStatement.GEQStatement(), value);
    }

    public <T> ConditionDef geq(T value, Predicate<T> fn) {
        return geq(value).when(fn.test(value));
    }

    public ConditionDef gt(Object value) {
        return conditionDef(new OperStatement.GTStatement(), value);
    }

    public <T> ConditionDef gt(T value, Predicate<T> fn) {
        return gt(value).when(fn.test(value));
    }

    public ConditionDef in(Object... values) {
        return in(Arrays.asList(values));
    }

    public ConditionDef in(Object[] values, Predicate<Object[]> fn) {
        return in(values).when(fn.test(values));
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

    public ConditionDef in(Collection<?> values, Predicate<Collection<?>> fn) {
        return in(values).when(fn.test(values));
    }

    public ConditionDef in(Query query) {
        BraceStatement braceStatement = new BraceStatement(query.statement());
        return conditionDef(new OperStatement.INStatement(), braceStatement);
    }

    public ConditionDef notIn(Object... values) {
        return not(in(Arrays.asList(values)));
    }

    public ConditionDef notIn(Object[] values, Predicate<Object[]> fn) {
        return notIn(values).when(fn.test(values));
    }

    public ConditionDef notIn(Collection<?> values) {
        return not(in(values));
    }

    public ConditionDef notIn(Collection<?> values, Predicate<Collection<?>> fn) {
        return not(in(values)).when(fn.test(values));
    }

    public ConditionDef notIn(Query query) {
        return not(in(query));
    }

    public ConditionDef like(Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        listColumnStatement.add(new FlexMarkInvokerStatement(value));
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        concatStatement.setParamsStatement(listColumnStatement);
        return conditionDef(new OperStatement.LIKEStatement(), concatStatement);
    }

    public <T> ConditionDef like(T value, Predicate<T> fn) {
        return like(value).when(fn.test(value));
    }

    public ConditionDef likeLeft(Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        listColumnStatement.add(new FlexMarkInvokerStatement(value));
        concatStatement.setParamsStatement(listColumnStatement);
        return conditionDef(new OperStatement.LIKEStatement(), concatStatement);
    }

    public <T> ConditionDef likeLeft(T value, Predicate<T> fn) {
        return likeLeft(value).when(fn.test(value));
    }

    public ConditionDef likeRight(Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new FlexMarkInvokerStatement(value));
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        concatStatement.setParamsStatement(listColumnStatement);
        return conditionDef(new OperStatement.LIKEStatement(), concatStatement);
    }

    public <T> ConditionDef likeRight(T value, Predicate<T> fn) {
        return likeRight(value).when(fn.test(value));
    }

    public ConditionDef notLike(Object value) {
        return not(like(value));
    }

    public <T> ConditionDef notLike(T value, Predicate<T> fn) {
        return notLike(value).when(fn.test(value));
    }

    public ConditionDef notLikeLeft(Object value) {
        return not(likeLeft(value));
    }

    public <T> ConditionDef notLikeLeft(T value, Predicate<T> fn) {
        return notLikeLeft(value).when(fn.test(value));
    }

    public ConditionDef notLikeRight(Object value) {
        return not(likeRight(value));
    }

    public <T> ConditionDef notLikeRight(T value, Predicate<T> fn) {
        return notLikeRight(value).when(fn.test(value));
    }

    public ConditionDef between(Object start, Object end) {
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

    public ConditionDef between(Object start, Object end, Predicate<Object[]> fn) {
        return between(start, end).when(fn.test(new Object[]{start, end}));
    }

    public ConditionDef notBetween(Object start, Object end) {
        return not(between(start, end));
    }

    public ConditionDef notBetween(Object start, Object end, Predicate<Object[]> fn) {
        return notBetween(start, end).when(fn.test(new Object[]{start, end}));
    }

    public ConditionDef isNull() {
        return conditionDef(new OperStatement.ISStatement(), new SymbolStatement.LetterStatement("NULL"));
    }

    public ConditionDef isNotNull() {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(this.getStatement());
        conditionStatement.setOper(new OperStatement.ISStatement());
        ConditionStatement rightConditionStatement = new ConditionStatement();
        rightConditionStatement.setOper(new OperStatement.NOTStatement());
        rightConditionStatement.setRight(new SymbolStatement.LetterStatement("NULL"));
        conditionStatement.setRight(rightConditionStatement);
        return new ConditionDef(conditionStatement);
    }

    protected ConditionDef not(ConditionDef conditionDef) {
        return FunctionDef.not(conditionDef);
    }

    public ColumnDef add(ColumnDef columnDef) {
        return new ColumnDef(conditionDef(new OperStatement.ADDStatement(), columnDef.getStatement()).getStatement());
    }

    public ColumnDef add(Number number) {
        return add(new ColumnDef(new SymbolStatement.LetterStatement(number.toString())));
    }

    public ColumnDef sub(ColumnDef columnDef) {
        return new ColumnDef(conditionDef(new OperStatement.SUBStatement(), columnDef.getStatement()).getStatement());
    }

    public ColumnDef sub(Number number) {
        return sub(new ColumnDef(new SymbolStatement.LetterStatement(number.toString())));
    }

    public ColumnDef multiply(ColumnDef columnDef) {
        return new ColumnDef(conditionDef(new OperStatement.STARStatement(), columnDef.getStatement()).getStatement());
    }

    public ColumnDef multiply(Number number) {
        return multiply(new ColumnDef(new SymbolStatement.LetterStatement(number.toString())));
    }

    public ColumnDef divide(ColumnDef columnDef) {
        return new ColumnDef(conditionDef(new OperStatement.DIVIDEStatement(), columnDef.getStatement()).getStatement());
    }

    public ColumnDef divide(Number number) {
        return divide(new ColumnDef(new SymbolStatement.LetterStatement(number.toString())));
    }

    protected ConditionDef conditionDef(OperStatement operStatement, Object value) {
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
