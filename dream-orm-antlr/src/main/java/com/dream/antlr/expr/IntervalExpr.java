package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.IntervalStatement;
import com.dream.antlr.smt.Statement;

/**
 * interval语法解析器
 */
public class IntervalExpr extends HelperExpr {
    private IntervalStatement intervalStatement;

    private Statement statement;
    public IntervalExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public IntervalExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.INTERVAL);
    }

    @Override
    protected Statement exprInterval(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        setExprTypes(ExprType.YEAR, ExprType.QUARTER, ExprType.MONTH, ExprType.WEEK, ExprType.DAY, ExprType.HOUR, ExprType.MINUTE, ExprType.SECOND);
        this.statement=statement;
        return expr();
    }

    @Override
    protected Statement exprYear(ExprInfo exprInfo) throws AntlrException {
        intervalStatement = new IntervalStatement.YearIntervalStatement();
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprQuarter(ExprInfo exprInfo) throws AntlrException {
        intervalStatement = new IntervalStatement.QuarterIntervalStatement();
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMonth(ExprInfo exprInfo) throws AntlrException {
        intervalStatement = new IntervalStatement.MonthIntervalStatement();
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprWeek(ExprInfo exprInfo) throws AntlrException {
        intervalStatement = new IntervalStatement.WeekIntervalStatement();
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDay(ExprInfo exprInfo) throws AntlrException {
        intervalStatement = new IntervalStatement.DayIntervalStatement();
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHour(ExprInfo exprInfo) throws AntlrException {
        intervalStatement = new IntervalStatement.HourIntervalStatement();
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMinute(ExprInfo exprInfo) throws AntlrException {
        intervalStatement = new IntervalStatement.MinuteIntervalStatement();
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSecond(ExprInfo exprInfo) throws AntlrException {
        intervalStatement = new IntervalStatement.SecondIntervalStatement();
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        intervalStatement.setStatement(statement);
        return intervalStatement;
    }
}
