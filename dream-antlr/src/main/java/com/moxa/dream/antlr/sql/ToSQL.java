package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.bind.ResultInfo;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.*;

public abstract class ToSQL {

    public abstract String getName();

    public ResultInfo toResult(PackageStatement statement, List<InvokerFactory> invokerFactoryList, Map<Class, Object> customObjList) throws InvokerException {
        ToAssist assist = new ToAssist(invokerFactoryList, customObjList);
        return new ResultInfo(toStr(statement, assist, null), assist.getSqlInvokerMap());
    }

    protected abstract String beforeCache(Statement statement);

    protected abstract void afterCache(Statement statement, String sql);

    public String toStr(Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        if (statement == null)
            return "";
        String sql = beforeCache(statement);
        if (sql != null)
            return sql;
        Queue<Handler> handlerQueue = null;
        if (!ObjectUtil.isNull(invokerList)) {
            handlerQueue = new ArrayDeque<>();
            ArrayList<Invoker> invokerArrayList = new ArrayList<>(invokerList);
            for (int i = invokerArrayList.size() - 1; i >= 0; i--) {
                Invoker invoker = invokerArrayList.get(i);
                if (invoker.isAccessible()) {
                    statement = assist.beforeChain(statement, this, handlerQueue, invoker.handle(), invokerList);
                    if (statement == null)
                        return "";
                }
            }
        }
        switch (statement.getNameId()) {
            case -1044920343://PackageStatement
                sql = toString((PackageStatement) statement, assist, invokerList);
                break;
            case 1282053276://BraceStatement
                sql = toString((BraceStatement) statement, assist, invokerList);
                break;
            case -749918540://ConditionStatement
                sql = toString((ConditionStatement) statement, assist, invokerList);
                break;
            case 1104960935://QueryStatement
                sql = toString((QueryStatement) statement, assist, invokerList);
                break;
            case -719831402://InsertStatement
                sql = toString((InsertStatement) statement, assist, invokerList);
                break;
            case 1587022413://ValuesStatement
                sql = toString((InsertStatement.ValuesStatement) statement, assist, invokerList);
                break;
            case -1876649338://UpdateStatement
                sql = toString((UpdateStatement) statement, assist, invokerList);
                break;
            case 1995025380://DeleteStatement
                sql = toString((DeleteStatement) statement, assist, invokerList);
                break;
            case 669165491://SelectStatement
                sql = toString((SelectStatement) statement, assist, invokerList);
                break;
            case 2141894352://PreSelectStatement
                sql = toString((PreSelectStatement) statement, assist, invokerList);
                break;
            case 267949595://ListColumnStatement
                sql = toString((ListColumnStatement) statement, assist, invokerList);
                break;
            case 1128438047://AliasStatement
                sql = toString((AliasStatement) statement, assist, invokerList);
                break;
            case -1787194369://CaseStatement
                sql = toString((CaseStatement) statement, assist, invokerList);
                break;
            case -1577722216://WhenThenStatement
                sql = toString((CaseStatement.WhenThenStatement) statement, assist, invokerList);
                break;
            case 1521151080://WhereStatement
                sql = toString((WhereStatement) statement, assist, invokerList);
                break;
            case 414383568://GroupStatement
                sql = toString((GroupStatement) statement, assist, invokerList);
                break;
            case 838579882://HavingStatement
                sql = toString((HavingStatement) statement, assist, invokerList);
                break;
            case 1833362913://OrderStatement
                sql = toString((OrderStatement) statement, assist, invokerList);
                break;
            case 1248285214://AscStatement
                sql = toString((OrderStatement.AscStatement) statement, assist, invokerList);
                break;
            case -1171239490://DescStatement
                sql = toString((OrderStatement.DescStatement) statement, assist, invokerList);
                break;
            case -428422124://LimitStatement
                sql = toString((LimitStatement) statement, assist, invokerList);
                break;
            case 615446400://UnionStatement
                sql = toString((UnionStatement) statement, assist, invokerList);
                break;
            case 1602604526://ADDStatement
                sql = toString((OperStatement.ADDStatement) statement, assist, invokerList);
                break;
            case -236816094://LLMStatement
                sql = toString((OperStatement.LLMStatement) statement, assist, invokerList);
                break;
            case -1654093342://RRMStatement
                sql = toString((OperStatement.RRMStatement) statement, assist, invokerList);
                break;
            case -459248849://SUBStatement
                sql = toString((OperStatement.SUBStatement) statement, assist, invokerList);
                break;
            case -477739203://STARStatement
                sql = toString((OperStatement.STARStatement) statement, assist, invokerList);
                break;
            case -135835402://DIVIDEStatement
                sql = toString((OperStatement.DIVIDEStatement) statement, assist, invokerList);
                break;
            case 565001389://MODStatement
                sql = toString((OperStatement.MODStatement) statement, assist, invokerList);
                break;
            case 1462204615://LTStatement
                sql = toString((OperStatement.LTStatement) statement, assist, invokerList);
                break;
            case -1329112489://LEQStatement
                sql = toString((OperStatement.LEQStatement) statement, assist, invokerList);
                break;
            case 1857026818://GTStatement
                sql = toString((OperStatement.GTStatement) statement, assist, invokerList);
                break;
            case -1974526084://GEQStatement
                sql = toString((OperStatement.GEQStatement) statement, assist, invokerList);
                break;
            case 1745502755://EQStatement
                sql = toString((OperStatement.EQStatement) statement, assist, invokerList);
                break;
            case -1070947051://NEQStatement
                sql = toString((OperStatement.NEQStatement) statement, assist, invokerList);
                break;
            case 1921203096://LIKEStatement
                sql = toString((OperStatement.LIKEStatement) statement, assist, invokerList);
                break;
            case -1540362395://ISStatement
                sql = toString((OperStatement.ISStatement) statement, assist, invokerList);
                break;
            case -557794870://INStatement
                sql = toString((OperStatement.INStatement) statement, assist, invokerList);
                break;
            case 1844835324://NOTStatement
                sql = toString((OperStatement.NOTStatement) statement, assist, invokerList);
                break;
            case 1750730867://EXISTSStatement
                sql = toString((OperStatement.EXISTSStatement) statement, assist, invokerList);
                break;
            case -49195369://SymbolStatement
                sql = toString((SymbolStatement) statement, assist, invokerList);
                break;
            case -918666854://SingleMarkStatement
                sql = toString((SingleMarkStatement) statement, assist, invokerList);
                break;
            case -1225453250://AsciiStatement
                sql = toString((FunctionStatement.AsciiStatement) statement, assist, invokerList);
                break;
            case -105938182://LenStatement
                sql = toString((FunctionStatement.LenStatement) statement, assist, invokerList);
                break;
            case 252356873://LengthStatement
                sql = toString((FunctionStatement.LengthStatement) statement, assist, invokerList);
                break;
            case 11485339://ConcatStatement
                sql = toString((FunctionStatement.ConcatStatement) statement, assist, invokerList);
                break;
            case -1658466212://GroupConcatStatement
                sql = toString((FunctionStatement.GroupConcatStatement) statement, assist, invokerList);
                break;
            case 231026207://ConcatWsStatement
                sql = toString((FunctionStatement.ConcatWsStatement) statement, assist, invokerList);
                break;
            case -1633513437://InStrStatement
                sql = toString((FunctionStatement.InStrStatement) statement, assist, invokerList);
                break;
            case -1760752099://LocateStatement
                sql = toString((FunctionStatement.LocateStatement) statement, assist, invokerList);
                break;
            case 1941061459://LcaseStatement
                sql = toString((FunctionStatement.LcaseStatement) statement, assist, invokerList);
                break;
            case -1907115122://LowerStatement
                sql = toString((FunctionStatement.LowerStatement) statement, assist, invokerList);
                break;
            case -678694648://LeftStatement
                sql = toString((FunctionStatement.LeftStatement) statement, assist, invokerList);
                break;
            case 1855648467://RightStatement
                sql = toString((FunctionStatement.RightStatement) statement, assist, invokerList);
                break;
            case -1634847071://LtrimStatement
                sql = toString((FunctionStatement.LtrimStatement) statement, assist, invokerList);
                break;
            case -1775150899://ReverseStatement
                sql = toString((FunctionStatement.ReverseStatement) statement, assist, invokerList);
                break;
            case -8608453://ReplaceStatement
                sql = toString((FunctionStatement.ReplaceStatement) statement, assist, invokerList);
                break;
            case -373231525://RtrimStatement
                sql = toString((FunctionStatement.RtrimStatement) statement, assist, invokerList);
                break;
            case 1690328190://SubStrStatement
                sql = toString((FunctionStatement.SubStrStatement) statement, assist, invokerList);
                break;
            case -1068135603://TrimStatement
                sql = toString((FunctionStatement.TrimStatement) statement, assist, invokerList);
                break;
            case 1864297257://SpaceStatement
                sql = toString((FunctionStatement.SpaceStatement) statement, assist, invokerList);
                break;
            case 935809805://UpperStatement
                sql = toString((FunctionStatement.UpperStatement) statement, assist, invokerList);
                break;
            case -14713752://LpadStatement
                sql = toString((FunctionStatement.LpadStatement) statement, assist, invokerList);
                break;
            case 1581904372://RepeatStatement
                sql = toString((FunctionStatement.RepeatStatement) statement, assist, invokerList);
                break;
            case -1775131794://RpadStatement
                sql = toString((FunctionStatement.RpadStatement) statement, assist, invokerList);
                break;
            case -1412528835://AbsStatement
                sql = toString((FunctionStatement.AbsStatement) statement, assist, invokerList);
                break;
            case -633655587://AvgStatement
                sql = toString((FunctionStatement.AvgStatement) statement, assist, invokerList);
                break;
            case -800573847://AcosStatement
                sql = toString((FunctionStatement.AcosStatement) statement, assist, invokerList);
                break;
            case 144123448://AsinStatement
                sql = toString((FunctionStatement.AsinStatement) statement, assist, invokerList);
                break;
            case -2095197289://SinStatement
                sql = toString((FunctionStatement.SinStatement) statement, assist, invokerList);
                break;
            case 1763915151://AtanStatement
                sql = toString((FunctionStatement.AtanStatement) statement, assist, invokerList);
                break;
            case 46464285://Atan2Statement
                sql = toString((FunctionStatement.Atan2Statement) statement, assist, invokerList);
                break;
            case -1856813622://CeilStatement
                sql = toString((FunctionStatement.CeilStatement) statement, assist, invokerList);
                break;
            case 651418962://CeilingStatement
                sql = toString((FunctionStatement.CeilingStatement) statement, assist, invokerList);
                break;
            case 1255072712://CosStatement
                sql = toString((FunctionStatement.CosStatement) statement, assist, invokerList);
                break;
            case 1058559207://CotStatement
                sql = toString((FunctionStatement.CotStatement) statement, assist, invokerList);
                break;
            case -1295940288://CountStatement
                sql = toString((FunctionStatement.CountStatement) statement, assist, invokerList);
                break;
            case -1184881678://ExpStatement
                sql = toString((FunctionStatement.ExpStatement) statement, assist, invokerList);
                break;
            case 184139491://FloorStatement
                sql = toString((FunctionStatement.FloorStatement) statement, assist, invokerList);
                break;
            case 647820781://LnStatement
                sql = toString((FunctionStatement.LnStatement) statement, assist, invokerList);
                break;
            case 480011947://LogStatement
                sql = toString((FunctionStatement.LogStatement) statement, assist, invokerList);
                break;
            case -1099829375://Log2Statement
                sql = toString((FunctionStatement.Log2Statement) statement, assist, invokerList);
                break;
            case -640291732://Log10Statement
                sql = toString((FunctionStatement.Log10Statement) statement, assist, invokerList);
                break;
            case 950847627://MaxStatement
                sql = toString((FunctionStatement.MaxStatement) statement, assist, invokerList);
                break;
            case 1425273693://MinStatement
                sql = toString((FunctionStatement.MinStatement) statement, assist, invokerList);
                break;
            case 1198635181://ModStatement
                sql = toString((FunctionStatement.ModStatement) statement, assist, invokerList);
                break;
            case -1262449834://PiStatement
                sql = toString((FunctionStatement.PiStatement) statement, assist, invokerList);
                break;
            case 2147094039://2147094039
                sql = toString((FunctionStatement.PowStatement) statement, assist, invokerList);
                break;
            case 365617674://PowerStatement
                sql = toString((FunctionStatement.PowerStatement) statement, assist, invokerList);
                break;
            case -1301936470://RandStatement
                sql = toString((FunctionStatement.RandStatement) statement, assist, invokerList);
                break;
            case -1440136287://RoundStatement
                sql = toString((FunctionStatement.RoundStatement) statement, assist, invokerList);
                break;
            case 1460912146://SignStatement
                sql = toString((FunctionStatement.SignStatement) statement, assist, invokerList);
                break;
            case -1272102897://SqrtStatement
                sql = toString((FunctionStatement.SqrtStatement) statement, assist, invokerList);
                break;
            case -1987263612://SumStatement
                sql = toString((FunctionStatement.SumStatement) statement, assist, invokerList);
                break;
            case -475405586://TanStatement
                sql = toString((FunctionStatement.TanStatement) statement, assist, invokerList);
                break;
            case 67432073://TruncateStatement
                sql = toString((FunctionStatement.TruncateStatement) statement, assist, invokerList);
                break;
            case -295868228://DateAddStatement
                sql = toString((FunctionStatement.DateAddStatement) statement, assist, invokerList);
                break;
            case -937838815://CurDateStatement
                sql = toString((FunctionStatement.CurDateStatement) statement, assist, invokerList);
                break;
            case -544809540://DateDiffStatement
                sql = toString((FunctionStatement.DateDiffStatement) statement, assist, invokerList);
                break;
            case -407410349://DayStatement
                sql = toString((FunctionStatement.DayStatement) statement, assist, invokerList);
                break;
            case 1061440597://StrToDateStatement
                sql = toString((FunctionStatement.StrToDateStatement) statement, assist, invokerList);
                break;
            case -954783318://DateForMatStatement
                sql = toString((FunctionStatement.DateForMatStatement) statement, assist, invokerList);
                break;
            case 639930334://ToCharStatement
                sql = toString((FunctionStatement.ToCharStatement) statement, assist, invokerList);
                break;
            case -485043189://ToNumberStatement
                sql = toString((FunctionStatement.ToNumberStatement) statement, assist, invokerList);
                break;
            case -2079680890://ToDateStatement
                sql = toString((FunctionStatement.ToDateStatement) statement, assist, invokerList);
                break;
            case 1095504383://DayOfYearStatement
                sql = toString((FunctionStatement.DayOfYearStatement) statement, assist, invokerList);
                break;
            case 994304971://HourStatement
                sql = toString((FunctionStatement.HourStatement) statement, assist, invokerList);
                break;
            case 1965625097://LastDayStatement
                sql = toString((FunctionStatement.LastDayStatement) statement, assist, invokerList);
                break;
            case 2134372763://MinuteStatement
                sql = toString((FunctionStatement.MinuteStatement) statement, assist, invokerList);
                break;
            case 1501217519://MonthStatement
                sql = toString((FunctionStatement.MonthStatement) statement, assist, invokerList);
                break;
            case 417418561://DateStatement
                sql = toString((FunctionStatement.DateStatement) statement, assist, invokerList);
                break;
            case 1888928601://NowStatement
                sql = toString((FunctionStatement.NowStatement) statement, assist, invokerList);
                break;
            case 979905603://QuarterStatement
                sql = toString((FunctionStatement.QuarterStatement) statement, assist, invokerList);
                break;
            case 863272763://SecondStatement
                sql = toString((FunctionStatement.SecondStatement) statement, assist, invokerList);
                break;
            case 165066792://DayOfWeekStatement
                sql = toString((FunctionStatement.DayOfWeekStatement) statement, assist, invokerList);
                break;
            case 1773061735://WeekOfYearStatement
                sql = toString((FunctionStatement.WeekOfYearStatement) statement, assist, invokerList);
                break;
            case 589882770://YearStatement
                sql = toString((FunctionStatement.YearStatement) statement, assist, invokerList);
                break;
            case 1847389910://YearDateOperStatement
                sql = toString((DateOperStatement.YearDateOperStatement) statement, assist, invokerList);
                break;
            case 268891271://QuarterDateOperStatement
                sql = toString((DateOperStatement.QuarterDateOperStatement) statement, assist, invokerList);
                break;
            case -1273094349://MonthDateOperStatement
                sql = toString((DateOperStatement.MonthDateOperStatement) statement, assist, invokerList);
                break;
            case 731339775://WeekDateOperStatement
                sql = toString((DateOperStatement.WeekDateOperStatement) statement, assist, invokerList);
                break;
            case -1861213801://DayDateOperStatement
                sql = toString((DateOperStatement.DayDateOperStatement) statement, assist, invokerList);
                break;
            case -1248418801://HourDateOperStatement
                sql = toString((DateOperStatement.HourDateOperStatement) statement, assist, invokerList);
                break;
            case -1228414497://MinuteDateOperStatement
                sql = toString((DateOperStatement.MinuteDateOperStatement) statement, assist, invokerList);
                break;
            case -890450049://SecondDateOperStatement
                sql = toString((DateOperStatement.SecondDateOperStatement) statement, assist, invokerList);
                break;
            case 552589950://IsNullStatement
                sql = toString((FunctionStatement.IsNullStatement) statement, assist, invokerList);
                break;
            case -1465082517://IfNullStatement
                sql = toString((FunctionStatement.IfNullStatement) statement, assist, invokerList);
                break;
            case -1082423960://CoalesceStatement
                sql = toString((FunctionStatement.CoalesceStatement) statement, assist, invokerList);
                break;
            case 1058233515://NullIfStatement
                sql = toString((FunctionStatement.NullIfStatement) statement, assist, invokerList);
                break;
            case -979151694://IfStatement
                sql = toString((FunctionStatement.IfStatement) statement, assist, invokerList);
                break;
            case -439929648://CastStatement
                sql = toString((FunctionStatement.CastStatement) statement, assist, invokerList);
                break;
            case 1125382932://SignedCastStatement
                sql = toString((CastTypeStatement.SignedCastStatement) statement, assist, invokerList);
                break;
            case -533298156://FloatCastStatement
                sql = toString((CastTypeStatement.FloatCastStatement) statement, assist, invokerList);
                break;
            case 1181056282://CharCastStatement
                sql = toString((CastTypeStatement.CharCastStatement) statement, assist, invokerList);
                break;
            case -1625821246://DateCastStatement
                sql = toString((CastTypeStatement.DateCastStatement) statement, assist, invokerList);
                break;
            case 1095030531://TimeCastStatement
                sql = toString((CastTypeStatement.TimeCastStatement) statement, assist, invokerList);
                break;
            case 486197493://DateTimeCastStatement
                sql = toString((CastTypeStatement.DateTimeCastStatement) statement, assist, invokerList);
                break;
            case 199426367://DecimalCastStatement
                sql = toString((CastTypeStatement.DecimalCastStatement) statement, assist, invokerList);
                break;

            case 6301980://ConvertStatement
                sql = toString((FunctionStatement.ConvertStatement) statement, assist, invokerList);
                break;
            case 1773440088://SignedConvertStatement
                sql = toString((ConvertTypeStatement.SignedConvertStatement) statement, assist, invokerList);
                break;
            case 1603887960://FloatConvertStatement
                sql = toString((ConvertTypeStatement.FloatConvertStatement) statement, assist, invokerList);
                break;
            case -1814133614://CharConvertStatement
                sql = toString((ConvertTypeStatement.CharConvertStatement) statement, assist, invokerList);
                break;
            case 1510682858://DateConvertStatement
                sql = toString((ConvertTypeStatement.DateConvertStatement) statement, assist, invokerList);
                break;
            case -511805943://TimeConvertStatement
                sql = toString((ConvertTypeStatement.TimeConvertStatement) statement, assist, invokerList);
                break;
            case -609949993://DateTimeConvertStatement
                sql = toString((ConvertTypeStatement.DateTimeConvertStatement) statement, assist, invokerList);
                break;
            case -1118612915://DecimalConvertStatement
                sql = toString((ConvertTypeStatement.DecimalConvertStatement) statement, assist, invokerList);
                break;
            case 689682023://BETWEENStatement
                sql = toString((OperStatement.BETWEENStatement) statement, assist, invokerList);
                break;
            case 812960120://ANDStatement
                sql = toString((OperStatement.ANDStatement) statement, assist, invokerList);
                break;
            case 105041637://BITANDStatement
                sql = toString((OperStatement.BITANDStatement) statement, assist, invokerList);
                break;
            case -233322561://BITORStatement
                sql = toString((OperStatement.BITORStatement) statement, assist, invokerList);
                break;
            case -1474196255://BITXORStatement
                sql = toString((OperStatement.BITXORStatement) statement, assist, invokerList);
                break;
            case 759344844://ORStatement
                sql = toString((OperStatement.ORStatement) statement, assist, invokerList);
                break;
            case 847732933://FromStatement
                sql = toString((FromStatement) statement, assist, invokerList);
                break;
            case -1697491280://CommaJoinStatement
                sql = toString((JoinStatement.CommaJoinStatement) statement, assist, invokerList);
                break;
            case 1666313854://LeftJoinStatement
                sql = toString((JoinStatement.LeftJoinStatement) statement, assist, invokerList);
                break;
            case -198896951://RightJoinStatement
                sql = toString((JoinStatement.RightJoinStatement) statement, assist, invokerList);
                break;
            case -133909297://InnerJoinStatement
                sql = toString((JoinStatement.InnerJoinStatement) statement, assist, invokerList);
                break;
            case 1286625061://CrossJoinStatement
                sql = toString((JoinStatement.CrossJoinStatement) statement, assist, invokerList);
                break;
            case 1749719820://RowNumberStatement
                sql = toString((RowNumberStatement) statement, assist, invokerList);
                break;
            case -1469486373://OverStatment
                sql = toString((RowNumberStatement.OverStatement) statement, assist, invokerList);
                break;
            case -1989568059://PartitionStatement
                sql = toString((RowNumberStatement.OverStatement.PartitionStatement) statement, assist, invokerList);
                break;
            case 792918965://InvokerStatement
                sql = toString((InvokerStatement) statement, assist, invokerList);
                break;
            case -996762854://CustomFunctionStatement
                sql = toString((MyFunctionStatement) statement, assist, invokerList);
                break;
            default:
                throw new InvokerException(statement.getClass().getName() + "未进行翻译，nameId：" + statement.getNameId());
        }
        if (assist != null && handlerQueue != null && !handlerQueue.isEmpty())
            sql = assist.afterChain(handlerQueue, sql);
        afterCache(statement, sql);
        return sql;
    }

    protected abstract String toString(PackageStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.RepeatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.InStrStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LocateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ToCharStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ToNumberStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.DateForMatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.StrToDateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(UnionStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(AliasStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(DateOperStatement.YearDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(DateOperStatement.QuarterDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(DateOperStatement.MonthDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(DateOperStatement.WeekDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(DateOperStatement.DayDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(DateOperStatement.HourDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(DateOperStatement.MinuteDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(DateOperStatement.SecondDateOperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(UpdateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(InsertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(InsertStatement.ValuesStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(DeleteStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(LimitStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OrderStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OrderStatement.AscStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OrderStatement.DescStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(HavingStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(GroupStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(WhereStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.LTStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.LEQStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.GTStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.GEQStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.EQStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.NEQStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.LIKEStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.ISStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.INStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.NOTStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.EXISTSStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.STARStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.DIVIDEStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.MODStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(BraceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(ListColumnStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(SymbolStatement statement, ToAssist assist, List<Invoker> invokerList);

    protected abstract String toString(SingleMarkStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(QueryStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(SelectStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(PreSelectStatement statement, ToAssist assist, List<Invoker> invokerList);

    protected abstract String toString(FunctionStatement.AsciiStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LenStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LengthStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ConcatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.GroupConcatStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ConcatWsStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LcaseStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LowerStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(ConditionStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LeftStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.RightStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LtrimStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ReverseStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ReplaceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.RtrimStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.SubStrStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.TrimStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.SpaceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.UpperStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.RpadStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.AbsStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.AvgStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.AcosStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.AsinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.SinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.AtanStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.Atan2Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.CeilStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.CeilingStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.CosStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.CotStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.CountStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ExpStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.FloorStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LnStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LogStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.Log2Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.Log10Statement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.MaxStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.MinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ModStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.PiStatement statement, ToAssist assist, List<Invoker> invokerList);

    protected abstract String toString(FunctionStatement.PowStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.PowerStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.RandStatement statement, ToAssist assist, List<Invoker> invokerList);

    protected abstract String toString(FunctionStatement.RoundStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.SignStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.SqrtStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.SumStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.TanStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.TruncateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.DateAddStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.CurDateStatement statement, ToAssist assist, List<Invoker> invokerList);

    protected abstract String toString(FunctionStatement.DateDiffStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.DayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.DayOfWeekStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.DayOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.HourStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.MinuteStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.LastDayStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.MonthStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.DateStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.NowStatement statement, ToAssist assist, List<Invoker> invokerList);

    protected abstract String toString(FunctionStatement.QuarterStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.SecondStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.WeekOfYearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.YearStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.IsNullStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.IfNullStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.CoalesceStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.NullIfStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.IfStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.CastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(CastTypeStatement.SignedCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(CastTypeStatement.FloatCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(CastTypeStatement.CharCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(CastTypeStatement.DateCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(CastTypeStatement.TimeCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(CastTypeStatement.DateTimeCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(CastTypeStatement.DecimalCastStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FunctionStatement.ConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(ConvertTypeStatement.SignedConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(ConvertTypeStatement.FloatConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(ConvertTypeStatement.CharConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(ConvertTypeStatement.DateConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(ConvertTypeStatement.TimeConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(ConvertTypeStatement.DateTimeConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(ConvertTypeStatement.DecimalConvertStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.ADDStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.LLMStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.RRMStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.SUBStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(CaseStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(CaseStatement.WhenThenStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.BETWEENStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.ANDStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.BITANDStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.BITORStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.BITXORStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(OperStatement.ORStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(FromStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(JoinStatement.CommaJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(JoinStatement.LeftJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(JoinStatement.RightJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(JoinStatement.InnerJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(JoinStatement.CrossJoinStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(RowNumberStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(RowNumberStatement.OverStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(RowNumberStatement.OverStatement.PartitionStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(InvokerStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

    protected abstract String toString(MyFunctionStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException;

}
