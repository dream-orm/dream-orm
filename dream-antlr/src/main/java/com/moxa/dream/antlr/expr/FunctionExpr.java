package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.Constant;
import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.*;

public class FunctionExpr extends SqlExpr {

    private Statement functionStatement;

    public FunctionExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(Constant.FUNCTION);
    }

    @Override
    public Statement nil() {
        return functionStatement;
    }

    @Override
    protected Statement exprAscii(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.AsciiStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLen(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LenStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLength(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LengthStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprConcat(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ConcatStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprGroupConcat(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.GroupConcatStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () ->
                new FunctionParamerExpr.GroupConcatExpr(exprReader)
        ).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFindInSet(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.FindInSetStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () ->
                new FunctionParamerExpr.GroupConcatExpr(exprReader)
        ).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCoalesce(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.CoalesceStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprConcatWs(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ConcatWsStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDateFormat(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.DateForMatStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStrToDate(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.StrToDateStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLcase(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LcaseStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLower(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LowerStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLeft(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LeftStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRight(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.RightStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprInstr(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.InStrStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLocate(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LocateStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLtrim(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LtrimStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRtrim(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.RtrimStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprTrim(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.TrimStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRepeat(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.RepeatStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprReverse(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ReverseStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprReplace(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ReplaceStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStrcmp(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.StrcmpStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSubStr(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.SubStrStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprUpper(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.UpperStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLpad(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LpadStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRpad(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.RpadStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAbs(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.AbsStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAcos(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.AcosStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAsin(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.AsinStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSin(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.SinStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAtan(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.AtanStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprToChar(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ToCharStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprToNumber(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ToNumberStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprToDate(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ToDateStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAvg(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.AvgStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () ->
                new FunctionParamerExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCeil(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.CeilStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCeiling(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.CeilingStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCos(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.CosStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCot(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.CotStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCount(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.CountStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () ->
                new FunctionParamerExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprExp(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ExpStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFloor(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.FloorStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLn(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LnStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLog(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LogStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLog2(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.Log2Statement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLog10(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.Log10Statement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMax(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.MaxStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () ->
                new FunctionParamerExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMin(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.MinStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () ->
                new FunctionParamerExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMod(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ModStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprPi(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.PiStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprPow(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.PowStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprPower(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.PowerStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRand(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.RandStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRound(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.RoundStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSign(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.SignStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSqrt(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.SqrtStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSum(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.SumStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () ->
                new FunctionParamerExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprTan(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.TanStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprTruncate(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.TruncateStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprNow(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.NowStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSysDate(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.SysDateStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDateAdd(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.DateAddStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                new FunctionParamerExpr.DateOperExpr(exprReader, true)
                , new ExprInfo(ExprType.COMMA, ","))).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDateSub(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.DateAddStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                new FunctionParamerExpr.DateOperExpr(exprReader, false)
                , new ExprInfo(ExprType.COMMA, ","))).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCurDate(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.CurDateStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDateDiff(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.DateDiffStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprYear(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.YearStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMonth(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.MonthStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprUnixTimeStamp(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.UnixTimeStampStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFromUnixTime(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.FromUnixTimeStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDate(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.DateStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDay(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.DayStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHour(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.HourStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMinute(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.MinuteStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSecond(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.SecondStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDayOfYear(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.DayOfYearStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDayOfWeek(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.DayOfWeekStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLastDay(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.LastDayStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprQuarter(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.QuarterStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprWeekOfYear(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.WeekOfYearStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSpace(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.SpaceStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprIf(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.IfStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprIfNull(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.IfNullStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprNullIf(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.NullIfStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprConvert(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.ConvertStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                new FunctionParamerExpr.ConvertExpr(exprReader)
                , new ExprInfo(ExprType.COMMA, ","))).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCast(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.CastStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                new FunctionParamerExpr.CastExpr(exprReader)
                , new ExprInfo(ExprType.COMMA, ","))).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprIsNull(ExprInfo exprInfo) {
        FunctionStatement func = new FunctionStatement.IsNullStatement();
        functionStatement = new FunctionParamerExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRowNumber(ExprInfo exprInfo) {
        return new RowNumberExpr(exprReader).expr();
    }


    @Override
    protected Statement exprMyFunction(ExprInfo exprInfo) {
        MyFunctionStatement myFunctionStatement = (MyFunctionStatement) exprInfo.getObjInfo();
        CustomFunctionExpr customFunctionExpr = new CustomFunctionExpr(exprReader, myFunctionStatement.getHelper(exprReader));
        functionStatement = customFunctionExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    public static class FunctionParamerExpr extends HelperExpr {
        private final FunctionStatement func;

        public FunctionParamerExpr(ExprReader exprReader, FunctionStatement func) {
            this(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                    new CompareExpr(exprReader)
                    , new ExprInfo(ExprType.COMMA, ",")));

        }

        public FunctionParamerExpr(ExprReader exprReader, FunctionStatement func, Helper helper) {
            super(exprReader, helper);
            this.func = func;
            setExprTypes(Constant.FUNCTION);

        }

        @Override
        protected Statement exprFunction(ExprInfo exprInfo) {
            push();
            func.setFunctionName(exprInfo.getInfo());
            setExprTypes(ExprType.LBRACE, ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprLBrace(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.HELP, ExprType.RBRACE);
            func.setParamsStatement(new ListColumnStatement());
            return expr();
        }

        @Override
        protected Statement exprRBrace(ExprInfo exprInfo) {
            setExprTypes(ExprType.NIL);
            push();
            return expr();
        }

        @Override
        public Statement nil() {
            if (func.getParamsStatement() != null)
                return func;
            else return new SymbolStatement.LetterStatement(func.getFunctionName());
        }

        @Override
        public Statement exprHelp(Statement statement) {
            func.setParamsStatement(statement);
            setExprTypes(ExprType.RBRACE);
            return expr();
        }

        public static class DistinctAllExpr extends HelperExpr {
            private final ListColumnStatement listColumnStatement = new ListColumnStatement(" ");

            public DistinctAllExpr(ExprReader exprReader) {
                this(exprReader, () -> new CompareExpr(exprReader));
            }

            public DistinctAllExpr(ExprReader exprReader, Helper helper) {
                super(exprReader, helper);
                setExprTypes(ExprType.DISTINCT, ExprType.ALL, ExprType.HELP);
            }

            @Override
            protected Statement exprDistinct(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.HELP);
                listColumnStatement.add(new SymbolStatement.LetterStatement("DISTINCT"));
                return expr();
            }

            @Override
            protected Statement exprAll(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.HELP);
                listColumnStatement.add(new SymbolStatement.LetterStatement("ALL"));
                return expr();
            }

            @Override
            public Statement nil() {
                return listColumnStatement;
            }

            @Override
            public Statement exprHelp(Statement statement) {
                listColumnStatement.add(statement);
                setExprTypes(ExprType.NIL);
                return expr();
            }
        }

        public static class GroupConcatExpr extends HelperExpr {
            private final ListColumnStatement listColumnStatement = new ListColumnStatement(" ");

            public GroupConcatExpr(ExprReader exprReader) {
                this(exprReader, () -> new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ",")));
            }

            public GroupConcatExpr(ExprReader exprReader, Helper helper) {
                super(exprReader, helper);
                setExprTypes(ExprType.DISTINCT, ExprType.ALL, ExprType.HELP);
            }

            @Override
            protected Statement exprDistinct(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.HELP);
                listColumnStatement.add(new SymbolStatement.LetterStatement("DISTINCT"));
                return expr();
            }

            @Override
            protected Statement exprAll(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.HELP);
                listColumnStatement.add(new SymbolStatement.LetterStatement("ALL"));
                return expr();
            }

            @Override
            protected Statement exprOrder(ExprInfo exprInfo) {
                QueryExpr.OrderExpr orderExpr = new QueryExpr.OrderExpr(exprReader);
                listColumnStatement.add(orderExpr.expr());
                setExprTypes(ExprType.SEPARATOR, ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprSeparator(ExprInfo exprInfo) {
                push();
                listColumnStatement.add(new SymbolStatement.LetterStatement("SEPARATOR"));
                ColumnExpr columnExpr = new ColumnExpr(exprReader);
                listColumnStatement.add(columnExpr.expr());
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            public Statement nil() {
                return listColumnStatement;
            }

            @Override
            public Statement exprHelp(Statement statement) {
                listColumnStatement.add(statement);
                setExprTypes(ExprType.ORDER, ExprType.NIL);
                return expr();
            }
        }

        public static class DateOperExpr extends HelperExpr {
            private final boolean positive;
            private DateOperStatement dateOperStatement;
            private Statement date;
            private Statement qty;

            public DateOperExpr(ExprReader exprReader, boolean positive) {
                this(exprReader, () -> new CompareExpr(exprReader), positive);

            }

            public DateOperExpr(ExprReader exprReader, Helper helper, boolean positive) {
                super(exprReader, helper);
                this.positive = positive;
            }

            @Override
            protected Statement exprYear(ExprInfo exprInfo) {
                dateOperStatement = new DateOperStatement.YearDateOperStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprQuarter(ExprInfo exprInfo) {
                dateOperStatement = new DateOperStatement.QuarterDateOperStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprMonth(ExprInfo exprInfo) {
                dateOperStatement = new DateOperStatement.MonthDateOperStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprWeek(ExprInfo exprInfo) {
                dateOperStatement = new DateOperStatement.WeekDateOperStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDay(ExprInfo exprInfo) {
                dateOperStatement = new DateOperStatement.DayDateOperStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprHour(ExprInfo exprInfo) {
                dateOperStatement = new DateOperStatement.HourDateOperStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprMinute(ExprInfo exprInfo) {
                dateOperStatement = new DateOperStatement.MinuteDateOperStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprSecond(ExprInfo exprInfo) {
                dateOperStatement = new DateOperStatement.SecondDateOperStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprInterval(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.HELP);
                return expr();
            }

            @Override
            public Statement exprHelp(Statement statement) {
                if (date == null) {
                    date = statement;
                    setExprTypes(ExprType.COMMA);
                } else {
                    qty = statement;
                    setExprTypes(ExprType.YEAR, ExprType.QUARTER, ExprType.MONTH, ExprType.WEEK, ExprType.DAY, ExprType.HOUR, ExprType.MINUTE, ExprType.SECOND);
                }
                return expr();
            }

            @Override
            protected Statement exprComma(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.INTERVAL, ExprType.HELP);
                return expr();
            }

            @Override
            public Statement nil() {
                dateOperStatement.setPositive(positive);
                dateOperStatement.setDate(date);
                dateOperStatement.setQty(qty);
                return dateOperStatement;
            }
        }

        public static class ConvertExpr extends HelperExpr {
            private ConvertTypeStatement convertTypeStatement;
            private Statement statement;

            public ConvertExpr(ExprReader exprReader) {
                this(exprReader, () -> new CompareExpr(exprReader));
            }

            public ConvertExpr(ExprReader exprReader, Helper helper) {
                super(exprReader, helper);
            }

            @Override
            protected Statement exprComma(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.CHAR, ExprType.SIGNED, ExprType.FLOAT, ExprType.DATE, ExprType.TIME, ExprType.DATETIME, ExprType.DECIMAL);
                return expr();
            }

            @Override
            protected Statement exprChar(ExprInfo exprInfo) {
                convertTypeStatement = new ConvertTypeStatement.CharConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDate(ExprInfo exprInfo) {
                convertTypeStatement = new ConvertTypeStatement.DateConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprTime(ExprInfo exprInfo) {
                convertTypeStatement = new ConvertTypeStatement.TimeConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDateTime(ExprInfo exprInfo) {
                convertTypeStatement = new ConvertTypeStatement.DateTimeConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprSigned(ExprInfo exprInfo) {
                convertTypeStatement = new ConvertTypeStatement.SignedConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL, ExprType.INTEGER, ExprType.INT);
                return expr();
            }

            @Override
            protected Statement exprFloat(ExprInfo exprInfo) {
                convertTypeStatement = new ConvertTypeStatement.FloatConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprInt(ExprInfo exprInfo) {
                return exprInteger(exprInfo);
            }

            @Override
            protected Statement exprInteger(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDecimal(ExprInfo exprInfo) {
                convertTypeStatement = new ConvertTypeStatement.DecimalConvertStatement(statement);
                push();
                setExprTypes(ExprType.LBRACE, ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprLBrace(ExprInfo exprInfo) {
                BraceExpr braceExpr = new BraceExpr(exprReader);
                BraceStatement braceStatement = (BraceStatement) braceExpr.expr();
                ((ConvertTypeStatement.DecimalConvertStatement) convertTypeStatement).setParamStatement(braceStatement);
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement nil() {
                return convertTypeStatement;
            }

            @Override
            public Statement exprHelp(Statement statement) {
                this.statement = statement;
                setExprTypes(ExprType.COMMA);
                return expr();
            }
        }

        public static class CastExpr extends HelperExpr {
            private CastTypeStatement castTypeStatement;
            private Statement statement;

            public CastExpr(ExprReader exprReader) {
                this(exprReader, () -> new CompareExpr(exprReader));
            }

            public CastExpr(ExprReader exprReader, Helper helper) {
                super(exprReader, helper);
            }

            @Override
            protected Statement exprAs(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.CHAR, ExprType.SIGNED, ExprType.FLOAT, ExprType.DATE, ExprType.TIME, ExprType.DATETIME, ExprType.DECIMAL);
                return expr();
            }

            @Override
            protected Statement exprChar(ExprInfo exprInfo) {
                castTypeStatement = new CastTypeStatement.CharCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDate(ExprInfo exprInfo) {
                castTypeStatement = new CastTypeStatement.DateCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprTime(ExprInfo exprInfo) {
                castTypeStatement = new CastTypeStatement.TimeCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDateTime(ExprInfo exprInfo) {
                castTypeStatement = new CastTypeStatement.DateTimeCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprSigned(ExprInfo exprInfo) {
                castTypeStatement = new CastTypeStatement.SignedCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL, ExprType.INTEGER, ExprType.INT);
                return expr();
            }

            @Override
            protected Statement exprFloat(ExprInfo exprInfo) {
                castTypeStatement = new CastTypeStatement.FloatCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprInt(ExprInfo exprInfo) {
                return exprInteger(exprInfo);
            }

            @Override
            protected Statement exprInteger(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDecimal(ExprInfo exprInfo) {
                castTypeStatement = new CastTypeStatement.DecimalCastStatement(statement);
                push();
                setExprTypes(ExprType.LBRACE, ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprLBrace(ExprInfo exprInfo) {
                BraceExpr braceExpr = new BraceExpr(exprReader);
                BraceStatement braceStatement = (BraceStatement) braceExpr.expr();
                ((CastTypeStatement.DecimalCastStatement) castTypeStatement).setParamStatement(braceStatement);
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement nil() {
                return castTypeStatement;
            }

            @Override
            public Statement exprHelp(Statement statement) {
                this.statement = statement;
                setExprTypes(ExprType.AS);
                return expr();
            }
        }
    }

    public static class RowNumberExpr extends SqlExpr {
        private RowNumberStatement rowNumberStatement;

        public RowNumberExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.ROW_NUMBER);
        }

        @Override
        protected Statement exprRowNumber(ExprInfo exprInfo) {
            rowNumberStatement = new RowNumberStatement();
            push();
            setExprTypes(ExprType.LBRACE);
            return expr();
        }

        @Override
        protected Statement exprLBrace(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.RBRACE);
            return expr();
        }

        @Override
        protected Statement exprRBrace(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.OVER);
            return expr();
        }

        @Override
        protected Statement exprOver(ExprInfo exprInfo) {
            rowNumberStatement.setStatement(new OverExpr(exprReader).expr());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement nil() {
            return rowNumberStatement;
        }

        public static class OverExpr extends SqlExpr {
            private RowNumberStatement.OverStatement overStatement;

            public OverExpr(ExprReader exprReader) {
                super(exprReader);
                setExprTypes(ExprType.OVER);
            }

            @Override
            protected Statement exprOver(ExprInfo exprInfo) {
                overStatement = new RowNumberStatement.OverStatement();
                push();
                setExprTypes(ExprType.LBRACE);
                return expr();
            }

            @Override
            protected Statement exprLBrace(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.PARTITION, ExprType.ORDER, ExprType.RBRACE);
                return expr();
            }

            @Override
            protected Statement exprRBrace(ExprInfo exprInfo) {
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprPartition(ExprInfo exprInfo) {
                overStatement.setPartitionStatement(new PartitionExpr(exprReader).expr());
                setExprTypes(ExprType.ORDER, ExprType.RBRACE);
                return expr();
            }

            @Override
            protected Statement exprOrder(ExprInfo exprInfo) {
                overStatement.setOrderStatement(new QueryExpr.OrderExpr(exprReader).expr());
                setExprTypes(ExprType.RBRACE);
                return expr();
            }

            @Override
            protected Statement nil() {
                return overStatement;
            }

            public static class PartitionExpr extends HelperExpr {
                private RowNumberStatement.OverStatement.PartitionStatement partitionStatement;

                public PartitionExpr(ExprReader exprReader) {
                    this(exprReader, () -> new ColumnExpr(exprReader));
                }


                public PartitionExpr(ExprReader exprReader, Helper helper) {
                    super(exprReader, helper);
                    setExprTypes(ExprType.PARTITION);
                }

                @Override
                protected Statement exprPartition(ExprInfo exprInfo) {
                    partitionStatement = new RowNumberStatement.OverStatement.PartitionStatement();
                    push();
                    setExprTypes(ExprType.BY);
                    return expr();
                }

                @Override
                protected Statement exprBy(ExprInfo exprInfo) {
                    push();
                    setExprTypes(ExprType.HELP);
                    return expr();
                }

                @Override
                public Statement exprHelp(Statement statement) {
                    partitionStatement.setStatement(statement);
                    setExprTypes(ExprType.NIL);
                    return expr();
                }

                @Override
                protected Statement nil() {
                    return partitionStatement;
                }
            }
        }
    }

    public static class CustomFunctionExpr extends HelperExpr {
        private MyFunctionStatement myFunctionStatement;

        public CustomFunctionExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
            setExprTypes(ExprType.MY_FUNCTION);
        }

        @Override
        protected Statement exprMyFunction(ExprInfo exprInfo) {
            push();
            this.myFunctionStatement = (MyFunctionStatement) exprInfo.getObjInfo();
            setExprTypes(ExprType.LBRACE, ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprLBrace(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.HELP);
            return expr();
        }

        @Override
        public Statement exprHelp(Statement statement) {
            myFunctionStatement.setParamsStatement(statement);
            setExprTypes(ExprType.RBRACE);
            return expr();
        }

        @Override
        protected Statement exprRBrace(ExprInfo exprInfo) {
            push();
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement nil() {
            return myFunctionStatement;
        }
    }
}
