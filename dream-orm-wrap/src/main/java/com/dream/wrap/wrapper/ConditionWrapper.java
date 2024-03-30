package com.dream.wrap.wrapper;

import com.dream.antlr.smt.*;
import com.dream.wrap.invoker.LambdaMarkInvokerStatement;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public abstract class ConditionWrapper<Children extends ConditionWrapper> {
    protected ConditionStatement conditionStatement = null;

    public Children eq(String column, Object value) {
        return condition(column, new OperStatement.EQStatement(), value);
    }


    public Children neq(String column, Object value) {
        return condition(column, new OperStatement.NEQStatement(), value);
    }


    public Children leq(String column, Object value) {
        return condition(column, new OperStatement.LEQStatement(), value);
    }


    public Children lt(String column, Object value) {
        return condition(column, new OperStatement.LTStatement(), value);
    }


    public Children geq(String column, Object value) {
        return condition(column, new OperStatement.GEQStatement(), value);
    }


    public Children gt(String column, Object value) {
        return condition(column, new OperStatement.GTStatement(), value);
    }


    public Children in(String column, Object... values) {
        return in(column, Arrays.asList(values));
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


    public Children in(String column, QueryWrapper queryWrapper) {
        BraceStatement braceStatement = new BraceStatement(queryWrapper.statement());
        return condition(column, new OperStatement.INStatement(), braceStatement);
    }

    public Children notIn(String column, Object... values) {
        return not(in(column, Arrays.asList(values)));
    }


    public Children notIn(String column, Collection<?> values) {
        return not(in(column, values));
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

    public Children likeLeft(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new LambdaMarkInvokerStatement(value));
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        concatStatement.setParamsStatement(listColumnStatement);
        return condition(column, new OperStatement.LIKEStatement(), concatStatement);
    }


    public Children likeRight(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new SymbolStatement.StrStatement("%"));
        listColumnStatement.add(new LambdaMarkInvokerStatement(value));
        concatStatement.setParamsStatement(listColumnStatement);
        return condition(column, new OperStatement.LIKEStatement(), concatStatement);
    }


    public Children notLike(String column, Object value) {
        return not(like(column, value));
    }


    public Children notLikeLeft(String column, Object value) {
        return not(likeLeft(column, value));
    }


    public Children notLikeRight(String column, Object value) {
        return not(likeRight(column, value));
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

    public Children notBetween(String column, Object start, Object end) {
        return not(between(column, start, end));
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

    public Children and(Consumer<Children> fn) {
//        return condition(fn.apply(columnWrapper), new OperStatement.ANDStatement());
        return (Children) this;
    }

    public Children or(Consumer<Children> fn) {
        return condition(true, null, null, null);
    }

    protected Children condition(String column, OperStatement operStatement, Object value) {
        return condition(new SymbolStatement.SingleMarkStatement(column), operStatement, new LambdaMarkInvokerStatement(value));
    }

    protected Children condition(Statement columnStatement, OperStatement operStatement, Statement valueStatement) {
        return condition(false, columnStatement, operStatement, valueStatement);
    }

    protected abstract Children condition(boolean or, Statement columnStatement, OperStatement operStatement, Statement valueStatement);

}
