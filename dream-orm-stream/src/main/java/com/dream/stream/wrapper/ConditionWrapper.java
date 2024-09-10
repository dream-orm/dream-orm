package com.dream.stream.wrapper;

import com.dream.antlr.smt.*;
import com.dream.antlr.util.AntlrUtil;
import com.dream.struct.invoker.TakeMarkInvokerStatement;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

public abstract class ConditionWrapper<Children extends ConditionWrapper<Children>> {

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
        listColumnStatement.add(values.stream().map(value -> new TakeMarkInvokerStatement(column, value)).toArray(Statement[]::new));
        BraceStatement braceStatement = new BraceStatement(listColumnStatement);
        return condition(column, new OperStatement.INStatement(), braceStatement);
    }


    public <T> Children in(String column, QueryWrapper<T> queryWrapper) {
        BraceStatement braceStatement = new BraceStatement(queryWrapper.statement());
        return condition(column, new OperStatement.INStatement(), braceStatement);
    }

    public Children notIn(String column, Object... values) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(Arrays.stream(values).map(value -> new TakeMarkInvokerStatement(column, value)).toArray(Statement[]::new));
        BraceStatement braceStatement = new BraceStatement(listColumnStatement);
        ConditionStatement inConditionStatement = AntlrUtil.conditionStatement(null, new OperStatement.INStatement(), braceStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.NOTStatement(), inConditionStatement);
    }


    public Children notIn(String column, Collection<?> values) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(values.stream().map(value -> new TakeMarkInvokerStatement(column, value)).toArray(Statement[]::new));
        BraceStatement braceStatement = new BraceStatement(listColumnStatement);
        ConditionStatement inConditionStatement = AntlrUtil.conditionStatement(null, new OperStatement.INStatement(), braceStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.NOTStatement(), inConditionStatement);
    }


    public <T> Children notIn(String column, QueryWrapper<T> queryWrapper) {
        BraceStatement braceStatement = new BraceStatement(queryWrapper.statement());
        ConditionStatement inConditionStatement = AntlrUtil.conditionStatement(null, new OperStatement.INStatement(), braceStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.NOTStatement(), inConditionStatement);
    }

    public Children like(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new Statement[]{new SymbolStatement.StrStatement("%"), new TakeMarkInvokerStatement(column, value), new SymbolStatement.StrStatement("%")});
        concatStatement.setParamsStatement(listColumnStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.LIKEStatement(), concatStatement);
    }

    public Children likeLeft(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new Statement[]{new TakeMarkInvokerStatement(column, value), new SymbolStatement.StrStatement("%")});
        concatStatement.setParamsStatement(listColumnStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.LIKEStatement(), concatStatement);
    }


    public Children likeRight(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new Statement[]{new SymbolStatement.StrStatement("%"), new TakeMarkInvokerStatement(column, value)});
        concatStatement.setParamsStatement(listColumnStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.LIKEStatement(), concatStatement);
    }


    public Children notLike(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new Statement[]{new SymbolStatement.StrStatement("%"), new TakeMarkInvokerStatement(column, value), new SymbolStatement.StrStatement("%")});
        concatStatement.setParamsStatement(listColumnStatement);
        ConditionStatement likeConditionStatement = AntlrUtil.conditionStatement(null, new OperStatement.LIKEStatement(), concatStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.NOTStatement(), likeConditionStatement);
    }


    public Children notLikeLeft(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new Statement[]{new TakeMarkInvokerStatement(column, value), new SymbolStatement.StrStatement("%")});
        concatStatement.setParamsStatement(listColumnStatement);
        ConditionStatement likeLeftConditionStatement = AntlrUtil.conditionStatement(null, new OperStatement.LIKEStatement(), concatStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.NOTStatement(), likeLeftConditionStatement);
    }


    public Children notLikeRight(String column, Object value) {
        FunctionStatement.ConcatStatement concatStatement = new FunctionStatement.ConcatStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(new Statement[]{new SymbolStatement.StrStatement("%"), new TakeMarkInvokerStatement(column, value)});
        concatStatement.setParamsStatement(listColumnStatement);
        ConditionStatement likeRightConditionStatement = AntlrUtil.conditionStatement(null, new OperStatement.LIKEStatement(), concatStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.NOTStatement(), likeRightConditionStatement);
    }


    public Children between(String column, Object start, Object end) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(new SymbolStatement.LetterStatement(column));
        conditionStatement.setOper(new OperStatement.BETWEENStatement());
        ConditionStatement rightConditionStatement = new ConditionStatement();
        rightConditionStatement.setLeft(new TakeMarkInvokerStatement(column, start));
        rightConditionStatement.setOper(new OperStatement.ANDStatement());
        rightConditionStatement.setRight(new TakeMarkInvokerStatement(column, end));
        conditionStatement.setRight(rightConditionStatement);
        return condition(new OperStatement.ANDStatement(), conditionStatement);
    }

    public Children notBetween(String column, Object start, Object end) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setOper(new OperStatement.BETWEENStatement());
        ConditionStatement rightConditionStatement = new ConditionStatement();
        rightConditionStatement.setLeft(new TakeMarkInvokerStatement(column, start));
        rightConditionStatement.setOper(new OperStatement.ANDStatement());
        rightConditionStatement.setRight(new TakeMarkInvokerStatement(column, end));
        conditionStatement.setRight(rightConditionStatement);
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.NOTStatement(), conditionStatement);
    }


    public Children isNull(String column) {
        return condition(column, new OperStatement.ISStatement(), new SymbolStatement.LetterStatement("NULL"));
    }

    public Children isNotNull(String column) {
        return condition(new SymbolStatement.LetterStatement(column), new OperStatement.ISStatement(), AntlrUtil.conditionStatement(null, new OperStatement.NOTStatement(), new SymbolStatement.LetterStatement("NULL")));
    }

    public Children and(Consumer<StatementConditionWrapper> fn) {
        StatementConditionWrapper statementConditionWrapper = new StatementConditionWrapper();
        fn.accept(statementConditionWrapper);
        Statement statement = statementConditionWrapper.statement();
        OperStatement.ANDStatement andStatement = new OperStatement.ANDStatement();
        if (statement instanceof ConditionStatement) {
            ConditionStatement conditionStatement = (ConditionStatement) statement;
            if (conditionStatement.getOper().getLevel() < andStatement.getLevel()) {
                return condition(andStatement, new BraceStatement(conditionStatement));
            }
        }
        return condition(andStatement, statement);

    }

    public Children or(Consumer<StatementConditionWrapper> fn) {
        StatementConditionWrapper statementConditionWrapper = new StatementConditionWrapper();
        fn.accept(statementConditionWrapper);
        return condition(new OperStatement.ORStatement(), statementConditionWrapper.statement());
    }

    protected Children condition(String column, OperStatement operStatement, Object value) {
        return condition(new SymbolStatement.LetterStatement(column), operStatement, new TakeMarkInvokerStatement(column, value));
    }

    protected Children condition(Statement columnStatement, OperStatement operStatement, Statement valueStatement) {
        return condition(new OperStatement.ANDStatement(), AntlrUtil.conditionStatement(columnStatement, operStatement, valueStatement));
    }

    protected abstract Children condition(OperStatement operStatement, Statement statement);

    public static class StatementConditionWrapper extends ConditionWrapper<StatementConditionWrapper> {
        private Statement statement;

        @Override
        protected StatementConditionWrapper condition(OperStatement operStatement, Statement statement) {
            if (this.statement == null) {
                this.statement = statement;
            } else {
                this.statement = AntlrUtil.conditionStatement(this.statement, operStatement, statement);
            }
            return this;
        }

        public Statement statement() {
            return this.statement;
        }
    }
}
