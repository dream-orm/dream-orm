package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.Constant;
import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
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
    protected Statement exprAscii(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.AsciiStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLen(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LenStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLength(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LengthStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprConcat(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ConcatStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprGroupConcat(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement.GroupConcatStatement func = new FunctionStatement.GroupConcatStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () -> new FunctionParamExpr.GroupConcatExpr(exprReader, func)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFindInSet(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.FindInSetStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCoalesce(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.CoalesceStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprConcatWs(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ConcatWsStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDateFormat(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.DateForMatStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStrToDate(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.StrToDateStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLcase(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LcaseStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLower(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LowerStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLeft(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LeftStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRight(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.RightStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprInstr(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.InStrStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLocate(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LocateStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLtrim(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LtrimStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRtrim(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.RtrimStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprTrim(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.TrimStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRepeat(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.RepeatStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprReverse(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ReverseStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprReplace(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ReplaceStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStrcmp(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.StrcmpStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSubStr(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.SubStrStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprUpper(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.UpperStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLpad(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LpadStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRpad(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.RpadStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAbs(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.AbsStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAcos(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.AcosStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAsin(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.AsinStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSin(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.SinStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAtan(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.AtanStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAvg(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.AvgStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () ->
                new FunctionParamExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCeil(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.CeilStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCeiling(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.CeilingStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCos(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.CosStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCot(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.CotStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCount(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.CountStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () ->
                new FunctionParamExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprExp(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ExpStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFloor(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.FloorStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLn(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LnStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLog(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LogStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLog2(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.Log2Statement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLog10(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.Log10Statement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMax(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.MaxStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () ->
                new FunctionParamExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMin(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.MinStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () ->
                new FunctionParamExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMod(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ModStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprPi(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.PiStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprPow(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.PowStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprPower(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.PowerStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRand(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.RandStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRound(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.RoundStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSign(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.SignStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSqrt(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.SqrtStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSum(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.SumStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () ->
                new FunctionParamExpr.DistinctAllExpr(exprReader)).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprTan(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.TanStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprTruncate(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.TruncateStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprNow(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.NowStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSysDate(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.SysDateStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDateAdd(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.DateAddStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                new FunctionParamExpr.DateAddExpr(exprReader)
                , new ExprInfo(ExprType.COMMA, ","))).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDateSub(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.DateAddStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                new FunctionParamExpr.DateSubExpr(exprReader)
                , new ExprInfo(ExprType.COMMA, ","))).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCurDate(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.CurDateStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDateDiff(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.DateDiffStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprYear(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.YearStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMonth(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.MonthStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprUnixTimeStamp(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.UnixTimeStampStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFromUnixTime(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.FromUnixTimeStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDate(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.DateStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDay(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.DayStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHour(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.HourStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMinute(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.MinuteStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSecond(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.SecondStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDayOfYear(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.DayOfYearStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDayOfWeek(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.DayOfWeekStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLastDay(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.LastDayStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprQuarter(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.QuarterStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprWeekOfYear(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.WeekOfYearStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSpace(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.SpaceStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprIf(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.IfStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprIfNull(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.IfNullStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprNullIf(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.NullIfStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprConvert(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ConvertStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                new FunctionParamExpr.ConvertExpr(exprReader)
                , new ExprInfo(ExprType.COMMA, ","))).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprCast(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.CastStatement();
        functionStatement = new FunctionParamExpr(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                new FunctionParamExpr.CastExpr(exprReader)
                , new ExprInfo(ExprType.COMMA, ","))).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprIsNull(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.IsNullStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprRowNumber(ExprInfo exprInfo) throws AntlrException {
        return new RowNumberExpr(exprReader).expr();
    }


    @Override
    protected Statement exprMyFunction(ExprInfo exprInfo) throws AntlrException {
        MyFunctionStatement myFunctionStatement = (MyFunctionStatement) exprInfo.getObjInfo();
        CustomFunctionExpr customFunctionExpr = new CustomFunctionExpr(exprReader, myFunctionStatement.getHelper(exprReader));
        functionStatement = customFunctionExpr.expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprToChar(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ToCharStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprToNumber(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ToNumberStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprToDate(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ToDateStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprToTimeStamp(ExprInfo exprInfo) throws AntlrException {
        FunctionStatement func = new FunctionStatement.ToTimeStampStatement();
        functionStatement = new FunctionParamExpr(exprReader, func).expr();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    public static class FunctionParamExpr extends HelperExpr {
        private final FunctionStatement func;

        public FunctionParamExpr(ExprReader exprReader, FunctionStatement func) {
            this(exprReader, func, () -> new ListColumnExpr(exprReader, () ->
                    new CompareExpr(exprReader)
                    , new ExprInfo(ExprType.COMMA, ",")));

        }

        public FunctionParamExpr(ExprReader exprReader, FunctionStatement func, Helper helper) {
            super(exprReader, helper);
            this.func = func;
            setExprTypes(Constant.FUNCTION);

        }

        @Override
        protected Statement exprFunction(ExprInfo exprInfo) throws AntlrException {
            push();
            func.setFunctionName(exprInfo.getInfo());
            setExprTypes(ExprType.LBRACE, ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.HELP, ExprType.RBRACE);
            func.setParamsStatement(new ListColumnStatement());
            return expr();
        }

        @Override
        protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
            setExprTypes(ExprType.NIL);
            push();
            return expr();
        }

        @Override
        public Statement nil() {
            return func;
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
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
            protected Statement exprDistinct(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.HELP);
                listColumnStatement.add(new SymbolStatement.LetterStatement("DISTINCT"));
                return expr();
            }

            @Override
            protected Statement exprAll(ExprInfo exprInfo) throws AntlrException {
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
            protected Statement exprHelp(Statement statement) throws AntlrException {
                listColumnStatement.add(statement);
                setExprTypes(ExprType.NIL);
                return expr();
            }
        }

        public static class DateAddExpr extends HelperExpr {
            private DateOperStatement dateOperStatement;
            private Statement date;
            private Statement qty;

            public DateAddExpr(ExprReader exprReader) {
                this(exprReader, () -> new CompareExpr(exprReader));

            }

            public DateAddExpr(ExprReader exprReader, Helper helper) {
                super(exprReader, helper);
            }

            @Override
            protected Statement exprYear(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.YearDateAddStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprQuarter(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.QuarterDateAddStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprMonth(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.MonthDateAddStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprWeek(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.WeekDateAddStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDay(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.DayDateAddStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprHour(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.HourDateAddStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprMinute(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.MinuteDateAddStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprSecond(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.SecondDateAddStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprInterval(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.HELP);
                return expr();
            }

            @Override
            protected Statement exprHelp(Statement statement) throws AntlrException {
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
            protected Statement exprComma(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.INTERVAL, ExprType.HELP);
                return expr();
            }

            @Override
            public Statement nil() {
                dateOperStatement.setDate(date);
                dateOperStatement.setQty(qty);
                return dateOperStatement;
            }
        }

        public static class DateSubExpr extends HelperExpr {
            private DateOperStatement dateOperStatement;
            private Statement date;
            private Statement qty;

            public DateSubExpr(ExprReader exprReader) {
                this(exprReader, () -> new CompareExpr(exprReader));

            }

            public DateSubExpr(ExprReader exprReader, Helper helper) {
                super(exprReader, helper);
            }

            @Override
            protected Statement exprYear(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.YearDateSubStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprQuarter(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.QuarterDateSubStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprMonth(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.MonthDateSubStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprWeek(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.WeekDateSubStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDay(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.DayDateSubStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprHour(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.HourDateSubStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprMinute(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.MinuteDateSubStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprSecond(ExprInfo exprInfo) throws AntlrException {
                dateOperStatement = new DateOperStatement.SecondDateSubStatement();
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprInterval(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.HELP);
                return expr();
            }

            @Override
            protected Statement exprHelp(Statement statement) throws AntlrException {
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
            protected Statement exprComma(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.INTERVAL, ExprType.HELP);
                return expr();
            }

            @Override
            public Statement nil() {
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
            protected Statement exprComma(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.CHAR, ExprType.SIGNED, ExprType.FLOAT, ExprType.DATE, ExprType.TIME, ExprType.DATETIME, ExprType.DECIMAL);
                return expr();
            }

            @Override
            protected Statement exprChar(ExprInfo exprInfo) throws AntlrException {
                convertTypeStatement = new ConvertTypeStatement.CharConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDate(ExprInfo exprInfo) throws AntlrException {
                convertTypeStatement = new ConvertTypeStatement.DateConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprTime(ExprInfo exprInfo) throws AntlrException {
                convertTypeStatement = new ConvertTypeStatement.TimeConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDateTime(ExprInfo exprInfo) throws AntlrException {
                convertTypeStatement = new ConvertTypeStatement.DateTimeConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprSigned(ExprInfo exprInfo) throws AntlrException {
                convertTypeStatement = new ConvertTypeStatement.SignedConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL, ExprType.INTEGER, ExprType.INT);
                return expr();
            }

            @Override
            protected Statement exprFloat(ExprInfo exprInfo) throws AntlrException {
                convertTypeStatement = new ConvertTypeStatement.FloatConvertStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprInt(ExprInfo exprInfo) throws AntlrException {
                return exprInteger(exprInfo);
            }

            @Override
            protected Statement exprInteger(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDecimal(ExprInfo exprInfo) throws AntlrException {
                convertTypeStatement = new ConvertTypeStatement.DecimalConvertStatement(statement);
                push();
                setExprTypes(ExprType.LBRACE, ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
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
            protected Statement exprHelp(Statement statement) throws AntlrException {
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
            protected Statement exprAs(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.CHAR, ExprType.SIGNED, ExprType.FLOAT, ExprType.DATE, ExprType.TIME, ExprType.DATETIME, ExprType.DECIMAL);
                return expr();
            }

            @Override
            protected Statement exprChar(ExprInfo exprInfo) throws AntlrException {
                castTypeStatement = new CastTypeStatement.CharCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDate(ExprInfo exprInfo) throws AntlrException {
                castTypeStatement = new CastTypeStatement.DateCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprTime(ExprInfo exprInfo) throws AntlrException {
                castTypeStatement = new CastTypeStatement.TimeCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDateTime(ExprInfo exprInfo) throws AntlrException {
                castTypeStatement = new CastTypeStatement.DateTimeCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprSigned(ExprInfo exprInfo) throws AntlrException {
                castTypeStatement = new CastTypeStatement.SignedCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL, ExprType.INTEGER, ExprType.INT);
                return expr();
            }

            @Override
            protected Statement exprFloat(ExprInfo exprInfo) throws AntlrException {
                castTypeStatement = new CastTypeStatement.FloatCastStatement(statement);
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprInt(ExprInfo exprInfo) throws AntlrException {
                return exprInteger(exprInfo);
            }

            @Override
            protected Statement exprInteger(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprDecimal(ExprInfo exprInfo) throws AntlrException {
                castTypeStatement = new CastTypeStatement.DecimalCastStatement(statement);
                push();
                setExprTypes(ExprType.LBRACE, ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
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
            protected Statement exprHelp(Statement statement) throws AntlrException {
                this.statement = statement;
                setExprTypes(ExprType.AS);
                return expr();
            }
        }

        public static class GroupConcatExpr extends HelperExpr {
            private FunctionStatement.GroupConcatStatement groupConcatStatement;

            public GroupConcatExpr(ExprReader exprReader, FunctionStatement.GroupConcatStatement groupConcatStatement) {
                this(exprReader, () -> new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ",")), groupConcatStatement);
            }

            public GroupConcatExpr(ExprReader exprReader, Helper helper, FunctionStatement.GroupConcatStatement groupConcatStatement) {
                super(exprReader, helper);
                setExprTypes(ExprType.DISTINCT, ExprType.ALL, ExprType.HELP);
                this.groupConcatStatement = groupConcatStatement;
            }

            @Override
            protected Statement exprDistinct(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.HELP);
                groupConcatStatement.setDistinct(true);
                return expr();
            }

            @Override
            protected Statement exprAll(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.HELP);
                groupConcatStatement.setAll(true);
                return expr();
            }

            @Override
            protected Statement exprOrder(ExprInfo exprInfo) throws AntlrException {
                OrderExpr orderExpr = new OrderExpr(exprReader);
                groupConcatStatement.setOrder(orderExpr.expr());
                setExprTypes(ExprType.SEPARATOR, ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprSeparator(ExprInfo exprInfo) throws AntlrException {
                push();
                ColumnExpr columnExpr = new ColumnExpr(exprReader);
                groupConcatStatement.setSeparator(columnExpr.expr());
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            public Statement nil() {
                return groupConcatStatement.getParamsStatement();
            }

            @Override
            protected Statement exprHelp(Statement statement) throws AntlrException {
                groupConcatStatement.setParamsStatement(statement);
                setExprTypes(ExprType.ORDER, ExprType.NIL);
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
        protected Statement exprRowNumber(ExprInfo exprInfo) throws AntlrException {
            rowNumberStatement = new RowNumberStatement();
            push();
            setExprTypes(ExprType.LBRACE);
            return expr();
        }

        @Override
        protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.RBRACE);
            return expr();
        }

        @Override
        protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.OVER);
            return expr();
        }

        @Override
        protected Statement exprOver(ExprInfo exprInfo) throws AntlrException {
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
            protected Statement exprOver(ExprInfo exprInfo) throws AntlrException {
                overStatement = new RowNumberStatement.OverStatement();
                push();
                setExprTypes(ExprType.LBRACE);
                return expr();
            }

            @Override
            protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.PARTITION, ExprType.ORDER, ExprType.RBRACE);
                return expr();
            }

            @Override
            protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprPartition(ExprInfo exprInfo) throws AntlrException {
                overStatement.setPartitionStatement(new PartitionExpr(exprReader).expr());
                setExprTypes(ExprType.ORDER, ExprType.RBRACE);
                return expr();
            }

            @Override
            protected Statement exprOrder(ExprInfo exprInfo) throws AntlrException {
                overStatement.setOrderStatement(new OrderExpr(exprReader).expr());
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
                protected Statement exprPartition(ExprInfo exprInfo) throws AntlrException {
                    partitionStatement = new RowNumberStatement.OverStatement.PartitionStatement();
                    push();
                    setExprTypes(ExprType.BY);
                    return expr();
                }

                @Override
                protected Statement exprBy(ExprInfo exprInfo) throws AntlrException {
                    push();
                    setExprTypes(ExprType.HELP);
                    return expr();
                }

                @Override
                protected Statement exprHelp(Statement statement) throws AntlrException {
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
        protected Statement exprMyFunction(ExprInfo exprInfo) throws AntlrException {
            push();
            this.myFunctionStatement = (MyFunctionStatement) exprInfo.getObjInfo();
            setExprTypes(ExprType.LBRACE, ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.HELP);
            return expr();
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
            myFunctionStatement.setParamsStatement(statement);
            setExprTypes(ExprType.RBRACE);
            return expr();
        }

        @Override
        protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
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
