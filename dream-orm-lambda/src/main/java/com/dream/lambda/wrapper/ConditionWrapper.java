package com.dream.lambda.wrapper;

import com.dream.antlr.smt.*;
import com.dream.lambda.invoker.LambdaMarkInvokerStatement;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class ConditionWrapper<Children extends ConditionWrapper> {

    public Children eq(String column, Object value) {
        return condition(column, new OperStatement.EQStatement(), value);
    }

    public <T> Children eq(String column, T value, Predicate<T> fn) {
        return (Children) eq(column, value).when(fn.test(value));
    }

    public Children neq(String column, Object value) {
        return condition(column, new OperStatement.NEQStatement(), value);
    }

    public <T> Children neq(String column, T value, Predicate<T> fn) {
        return (Children) neq(column, value).when(fn.test(value));
    }

    public Children leq(String column, Object value) {
        return condition(column, new OperStatement.LEQStatement(), value);
    }

    public <T> Children leq(String column, T value, Predicate<T> fn) {
        return (Children) leq(column, value).when(fn.test(value));
    }

    public Children lt(String column, Object value) {
        return condition(column, new OperStatement.LTStatement(), value);
    }

    public <T> Children lt(String column, T value, Predicate<T> fn) {
        return (Children) lt(column, value).when(fn.test(value));
    }

    public Children geq(String column, Object value) {
        return condition(column, new OperStatement.GEQStatement(), value);
    }

    public <T> Children geq(String column, T value, Predicate<T> fn) {
        return (Children) geq(column, value).when(fn.test(value));
    }

    public Children gt(String column, Object value) {
        return condition(column, new OperStatement.GTStatement(), value);
    }

    public <T> Children gt(String column, T value, Predicate<T> fn) {
        return (Children) gt(column, value).when(fn.test(value));
    }

    public Children in(String column, Object... values) {
        return in(column, Arrays.asList(values));
    }

    public Children in(String column, Object[] values, Predicate<Object[]> fn) {
        return (Children) in(column, values).when(fn.test(values));
    }

    public Children in(String column, Collection<?> values) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        Iterator<?> iterator = values.iterator();
        while (iterator.hasNext()) {
            Object value = iterator.next();
            listColumnStatement.add(new LambdaMarkInvokerStatement(value));
        }
        BraceStatement braceStatement = new BraceStatement(listColumnStatement);
        return condition(column, new OperStatement.INStatement(), braceStatement);
    }

    public Children in(String column, Collection<?> values, Predicate<Collection<?>> fn) {
        return (Children) in(column, values).when(fn.test(values));
    }

    public Children in(String column, QueryWrapper queryWrapper) {
        BraceStatement braceStatement = new BraceStatement(queryWrapper.statement());
        return condition(column, new OperStatement.INStatement(), braceStatement);
    }

    public Children notIn(String column, Object... values) {
        return not(in(column, Arrays.asList(values)));
    }

    public Children notIn(String column, Object[] values, Predicate<Object[]> fn) {
        return (Children) notIn(column, values).when(fn.test(values));
    }

    public Children notIn(String column, Collection<?> values) {
        return not(in(column, values));
    }

    public Children notIn(String column, Collection<?> values, Predicate<Collection<?>> fn) {
        return (Children) not(in(column, values)).when(fn.test(values));
    }

    public Children notIn(String column, SelectWrapper selectWrapper) {
        return not(in(column, selectWrapper));
    }

    public Children like(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        listColumnStatement.add(new LambdaMarkInvokerStatement(value));
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        concatStatement.setParamsStatement(listColumnStatement);
        return condition(column, new OperStatement.LIKEStatement(), concatStatement);
    }

    public <T> Children like(String column, T value, Predicate<T> fn) {
        return (Children) like(column, value).when(fn.test(value));
    }

    public Children likeLeft(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new LambdaMarkInvokerStatement(value));
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        concatStatement.setParamsStatement(listColumnStatement);
        return condition(column, new OperStatement.LIKEStatement(), concatStatement);
    }

    public <T> Children likeLeft(String column, T value, Predicate<T> fn) {
        return (Children) likeLeft(column, value).when(fn.test(value));
    }

    public Children likeRight(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        listColumnStatement.add(new LambdaMarkInvokerStatement(value));
        concatStatement.setParamsStatement(listColumnStatement);
        return condition(column, new OperStatement.LIKEStatement(), concatStatement);
    }

    public <T> Children likeRight(String column, T value, Predicate<T> fn) {
        return (Children) likeRight(column, value).when(fn.test(value));
    }

    public Children notLike(String column, Object value) {
        return not(like(column, value));
    }

    public <T> Children notLike(String column, T value, Predicate<T> fn) {
        return (Children) notLike(column, value).when(fn.test(value));
    }

    public Children notLikeLeft(String column, Object value) {
        return not(likeLeft(column, value));
    }

    public <T> Children notLikeLeft(String column, T value, Predicate<T> fn) {
        return (Children) notLikeLeft(column, value).when(fn.test(value));
    }

    public Children notLikeRight(String column, Object value) {
        return not(likeRight(column, value));
    }

    public <T> Children notLikeRight(String column, T value, Predicate<T> fn) {
        return (Children) notLikeRight(column, value).when(fn.test(value));
    }

    public Children between(String column, Object start, Object end) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(new SymbolStatement.SingleMarkStatement(column));
        conditionStatement.setOper(new OperStatement.BETWEENStatement());
        ConditionStatement rightConditionStatement = new ConditionStatement();
        rightConditionStatement.setLeft(new LambdaMarkInvokerStatement(start));
        rightConditionStatement.setOper(new OperStatement.ANDStatement());
        rightConditionStatement.setRight(new LambdaMarkInvokerStatement(end));
        conditionStatement.setRight(rightConditionStatement);
//        return new Children(this, conditionStatement);
        return null;
    }

    public Children between(String column, Object start, Object end, Predicate<Object[]> fn) {
        return (Children) between(column, start, end).when(fn.test(new Object[]{start, end}));
    }

    public Children notBetween(String column, Object start, Object end) {
        return not(between(column, start, end));
    }

    public Children notBetween(String column, Object start, Object end, Predicate<Object[]> fn) {
        return (Children) notBetween(column, start, end).when(fn.test(new Object[]{start, end}));
    }

    public Children isNull(String column) {
        return condition(column, new OperStatement.ISStatement(), new SymbolStatement.LetterStatement("NULL"));
    }

    public Children isNotNull(String column) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(new SymbolStatement.SingleMarkStatement(column));
        conditionStatement.setOper(new OperStatement.ISStatement());
        ConditionStatement rightConditionStatement = new ConditionStatement();
        rightConditionStatement.setOper(new OperStatement.NOTStatement());
        rightConditionStatement.setRight(new SymbolStatement.LetterStatement("NULL"));
        conditionStatement.setRight(rightConditionStatement);
//        return new Children(this, conditionStatement);
        return null;
    }

    protected Children not(Children condition) {
//        return FunctionDef.not(condition);
        return null;
    }

    public Children when(boolean effective) {
        if (!effective) {

        }
        return (Children) this;
    }

    public Children and(Consumer<Children> fn) {
//        return condition(fn.apply(columnWrapper), new OperStatement.ANDStatement());
        return (Children) this;
    }

    public Children or(Consumer<Children> fn) {
        return (Children) this;
    }

    protected Children condition(String column, OperStatement operStatement, Object value) {
        return condition(column, operStatement, new LambdaMarkInvokerStatement(value));
    }

    protected Children condition(String column, OperStatement operStatement, Statement valueStatement) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(new SymbolStatement.SingleMarkStatement(column));
        conditionStatement.setOper(operStatement);
        conditionStatement.setRight(valueStatement);
        this.accept(conditionStatement);
        return (Children) this;
    }

    protected abstract void accept(ConditionStatement statement);

}
