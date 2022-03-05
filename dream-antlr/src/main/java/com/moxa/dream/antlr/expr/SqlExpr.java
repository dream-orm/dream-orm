package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.util.ExprUtil;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class SqlExpr {

    protected Set<ExprType> acceptSet = new HashSet<>();
    protected ExprReader exprReader;
    protected boolean self;

    public SqlExpr(ExprReader exprReader) {
        this.exprReader = exprReader;
    }

    public ExprInfo push() {
        ExprInfo exprInfo = exprReader.push();
        exprReader.push(exprInfo);
        return exprInfo;
    }

    public Statement expr() {
        ObjectUtil.requireTrue(!isClose(), "SQL parsing is closed");
        tryMark();
        ExprInfo exprInfo = exprReader.getLastInfo();
        if (exprInfo == null)
            exprInfo = push();
        ExprType exprType = exprInfo.getExprType();
        if (!(self = exprBefore(exprInfo)))
            exprInfo.setExprType(ExprType.DEFAULT);
        Statement statement;
        switch (exprInfo.getExprType()) {
            case INT:
                statement = exprInt(exprInfo);
                break;
            case LONG:
                statement = exprLong(exprInfo);
                break;
            case FLOAT:
                statement = exprFloat(exprInfo);
                break;
            case DOUBLE:
                statement = exprDouble(exprInfo);
                break;
            case BOOLEAN:
                statement = exprBoolean(exprInfo);
                break;
            case STR:
                statement = exprStr(exprInfo);
                break;
            case LETTER:
                statement = exprLetter(exprInfo);
                break;
            case SKIP:
                statement = exprSkip(exprInfo);
                break;
            case LIKE:
                statement = exprLike(exprInfo);
                break;
            case BETWEEN:
                statement = exprBetween(exprInfo);
                break;
            case LBRACE:
                statement = exprLBrace(exprInfo);
                break;
            case RBRACE:
                statement = exprRBrace(exprInfo);
                break;
            case INTERVAL:
                statement = exprInterval(exprInfo);
                break;
            case COMMA:
                statement = exprComma(exprInfo);
                break;
            case SINGLE_MARK:
                statement = exprSingleMark(exprInfo);
                break;
            case MARK:
                statement = exprMark(exprInfo);
                break;
            case ON:
                statement = exprOn(exprInfo);
                break;
            case AS:
                statement = exprAs(exprInfo);
                break;
            case ASC:
                statement = exprAsc(exprInfo);
                break;
            case DESC:
                statement = exprDesc(exprInfo);
                break;
            case ASCII:
                statement = exprAscii(exprInfo);
                break;
            case CHARACTER_LENGTH:
            case CHAR_LENGTH:
            case LEN:
                statement = exprLen(exprInfo);
                break;
            case CONCAT_WS:
                statement = exprConcatWs(exprInfo);
                break;
            case INSTR:
                statement = exprInstr(exprInfo);
                break;
            case LOCATE:
                statement = exprLocate(exprInfo);
                break;
            case LCASE:
                statement = exprLcase(exprInfo);
                break;
            case LOWER:
                statement = exprLower(exprInfo);
                break;
            case LTRIM:
                statement = exprLtrim(exprInfo);
                break;
            case REPEAT:
                statement = exprRepeat(exprInfo);
                break;
            case REVERSE:
                statement = exprReverse(exprInfo);
                break;
            case REPLACE:
                statement = exprReplace(exprInfo);
                break;
            case RTRIM:
                statement = exprRtrim(exprInfo);
                break;
            case STRCMP:
                statement = exprStrcmp(exprInfo);
                break;
            case SUBSTR:
            case SUBSTRING:
                statement = exprSubStr(exprInfo);
                break;
            case SPACE:
                statement = exprSpace(exprInfo);
                break;
            case TRIM:
                statement = exprTrim(exprInfo);
                break;
            case UCASE:
            case UPPER:
                statement = exprUpper(exprInfo);
                break;
            case LPAD:
                statement = exprLpad(exprInfo);
                break;
            case RPAD:
                statement = exprRpad(exprInfo);
                break;
            case ABS:
                statement = exprAbs(exprInfo);
                break;
            case AVG:
                statement = exprAvg(exprInfo);
                break;
            case ACOS:
                statement = exprAcos(exprInfo);
                break;
            case ASIN:
                statement = exprAsin(exprInfo);
                break;
            case SIN:
                statement = exprSin(exprInfo);
                break;
            case ATAN:
                statement = exprAtan(exprInfo);
                break;
            case ALL:
                statement = exprAll(exprInfo);
                break;
            case CEIL:
                statement = exprCeil(exprInfo);
                break;
            case CEILING:
                statement = exprCeiling(exprInfo);
                break;
            case COS:
                statement = exprCos(exprInfo);
                break;
            case COT:
                statement = exprCot(exprInfo);
                break;
            case COUNT:
                statement = exprCount(exprInfo);
                break;
            case EXP:
                statement = exprExp(exprInfo);
                break;
            case FLOOR:
                statement = exprFloor(exprInfo);
                break;
            case LN:
                statement = exprLn(exprInfo);
                break;
            case LOG:
                statement = exprLog(exprInfo);
                break;
            case LOG10:
                statement = exprLog10(exprInfo);
                break;
            case LOG2:
                statement = exprLog2(exprInfo);
                break;
            case MAX:
                statement = exprMax(exprInfo);
                break;
            case MIN:
                statement = exprMin(exprInfo);
                break;
            case PI:
                statement = exprPi(exprInfo);
                break;
            case POW:
                statement = exprPow(exprInfo);
                break;
            case POWER:
                statement = exprPower(exprInfo);
                break;
            case RAND:
                statement = exprRand(exprInfo);
                break;
            case ROUND:
                statement = exprRound(exprInfo);
                break;
            case SIGN:
                statement = exprSign(exprInfo);
                break;
            case SQRT:
                statement = exprSqrt(exprInfo);
                break;
            case SUM:
                statement = exprSum(exprInfo);
                break;
            case TAN:
                statement = exprTan(exprInfo);
                break;
            case TRUNCATE:
                statement = exprTruncate(exprInfo);
                break;
            case ORDER:
                statement = exprOrder(exprInfo);
                break;
            case CURDATE:
                statement = exprCurDate(exprInfo);
                break;
            case DATEDIFF:
                statement = exprDateDiff(exprInfo);
                break;
            case DATE_ADD:
                statement = exprDateAdd(exprInfo);
                break;
            case DATE_SUB:
                statement = exprDateSub(exprInfo);
                break;
            case DATE_FORMAT:
                statement = exprDateFormat(exprInfo);
                break;
            case TO_CHAR:
                statement = exprToChar(exprInfo);
                break;
            case TO_NUMBER:
                statement = exprToNumber(exprInfo);
                break;
            case TO_DATE:
                statement = exprToDate(exprInfo);
                break;
            case DAY:
            case DAYOFMONTH:
                statement = exprDay(exprInfo);
                break;
            case DAYOFWEEK:
                statement = exprDayOfWeek(exprInfo);
                break;
            case DAYOFYEAR:
                statement = exprDayOfYear(exprInfo);
                break;
            case HOUR:
                statement = exprHour(exprInfo);
                break;
            case LAST_DAY:
                statement = exprLastDay(exprInfo);
                break;
            case MINUTE:
                statement = exprMinute(exprInfo);
                break;
            case MONTH:
                statement = exprMonth(exprInfo);
                break;
            case NOW:
                statement = exprNow(exprInfo);
                break;
            case QUARTER:
                statement = exprQuarter(exprInfo);
                break;
            case SECOND:
                statement = exprSecond(exprInfo);
                break;
            case TIME:
                statement = exprTime(exprInfo);
                break;
            case WEEK:
                statement = exprWeek(exprInfo);
                break;
            case WEEKOFYEAR:
                statement = exprWeekOfYear(exprInfo);
                break;
            case YEAR:
                statement = exprYear(exprInfo);
                break;
            case STR_TO_DATE:
                statement = exprStrToDate(exprInfo);
                break;
            case MY_FUNCTION:
                statement = exprMyFunction(exprInfo);
                break;
            case CONVERT:
                statement = exprConvert(exprInfo);
                break;
            case CAST:
                statement = exprCast(exprInfo);
                break;
            case CHAR:
                statement = exprChar(exprInfo);
                break;
            case DATE:
                statement = exprDate(exprInfo);
                break;
            case DATETIME:
                statement = exprDateTime(exprInfo);
                break;
            case SIGNED:
                statement = exprSigned(exprInfo);
                break;
            case INTEGER:
                statement = exprInteger(exprInfo);
                break;
            case DECIMAL:
                statement = exprDecimal(exprInfo);
                break;
            case COALESCE:
                statement = exprCoalesce(exprInfo);
                break;
            case CONCAT:
                statement = exprConcat(exprInfo);
                break;
            case IFNULL:
                statement = exprIfNull(exprInfo);
                break;
            case IF:
                statement = exprIf(exprInfo);
                break;
            case NULLIF:
                statement = exprNullIf(exprInfo);
                break;
            case OFFSET:
                statement = exprOffSet(exprInfo);
                break;
            case UNION:
                statement = exprUnion(exprInfo);
                break;
            case ISNULL:
                statement = exprIsNull(exprInfo);
                break;
            case CASE:
                statement = exprCase(exprInfo);
                break;
            case WHEN:
                statement = exprWhen(exprInfo);
                break;
            case THEN:
                statement = exprThen(exprInfo);
                break;
            case ELSE:
                statement = exprElse(exprInfo);
                break;
            case END:
                statement = exprEnd(exprInfo);
                break;
            case SELECT:
                statement = exprSelect(exprInfo);
                break;
            case FROM:
                statement = exprFrom(exprInfo);
                break;
            case WHERE:
                statement = exprWhere(exprInfo);
                break;
            case HAVING:
                statement = exprHaving(exprInfo);
                break;
            case LIMIT:
                statement = exprLimit(exprInfo);
                break;
            case DISTINCT:
                statement = exprDistinct(exprInfo);
                break;
            case AND:
                statement = exprAnd(exprInfo);
                break;
            case BITAND://&
                statement = exprBitAnd(exprInfo);
                break;
            case BITOR://|
                statement = exprBitOr(exprInfo);
                break;
            case BITXOR://^
                statement = exprBitXor(exprInfo);
                break;
            case OR:
                statement = exprOr(exprInfo);
                break;
            case EQ:
                statement = exprEq(exprInfo);
                break;
            case IS:
                statement = exprIs(exprInfo);
                break;
            case IN:
                statement = exprIn(exprInfo);
                break;
            case NOT:
                statement = exprNot(exprInfo);
                break;
            case EXISTS:
                statement = exprExists(exprInfo);
                break;
            case LT:
                statement = exprLt(exprInfo);
                break;
            case LLM:
                statement = exprLlm(exprInfo);
                break;
            case RRM:
                statement = exprRrm(exprInfo);
                break;
            case GT:
                statement = exprGt(exprInfo);
                break;
            case LEQ:
                statement = exprLeq(exprInfo);
                break;
            case GEQ:
                statement = exprGeq(exprInfo);
                break;
            case NEQ:
                statement = exprNeq(exprInfo);
                break;
            case LEFT:
                statement = exprLeft(exprInfo);
                break;
            case GROUP:
                statement = exprGroup(exprInfo);
                break;
            case BY:
                statement = exprBy(exprInfo);
                break;
            case INNER:
                statement = exprInner(exprInfo);
                break;
            case RIGHT:
                statement = exprRight(exprInfo);
                break;
            case CROSS:
                statement = exprCross(exprInfo);
                break;
            case OUTER:
                statement = exprOuter(exprInfo);
                break;
            case JOIN:
                statement = exprJoin(exprInfo);
                break;
            case ADD:
                statement = exprAdd(exprInfo);
                break;
            case SUB:
                statement = exprSub(exprInfo);
                break;
            case STAR:
                statement = exprStar(exprInfo);
                break;
            case DIVIDE:
                statement = exprDivide(exprInfo);
                break;
            case MOD:
                statement = exprMod(exprInfo);
                break;
            case INSERT:
                statement = exprInsert(exprInfo);
                break;
            case INTO:
                statement = exprInto(exprInfo);
                break;
            case VALUES:
                statement = exprValues(exprInfo);
                break;
            case DELETE:
                statement = exprDelete(exprInfo);
                break;
            case UPDATE:
                statement = exprUpdate(exprInfo);
                break;
            case SET:
                statement = exprSet(exprInfo);
                break;
            case ROW_NUMBER:
                statement = exprRowNumber(exprInfo);
                break;
            case OVER:
                statement = exprOver(exprInfo);
                break;
            case PARTITION:
                statement = exprPartition(exprInfo);
                break;
            case INVOKER:
                statement = exprInvoker(exprInfo);
                break;
            case COLON:
                statement = exprColon(exprInfo);
                break;
            case ACC:
                statement = exprAcc(exprInfo);
                break;
            default:
                exprInfo.setExprType(exprType);
                statement = exprDefault(exprInfo);
                break;
        }
        return statement;
    }

    protected Statement exprSingleMark(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprMark(ExprInfo exprInfo) {
        return exprSymbol(exprInfo);
    }

    protected Statement exprUnion(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprOffSet(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprColon(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprInvoker(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprRepeat(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprToChar(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprToNumber(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprToDate(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprSin(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprSpace(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLpad(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprRpad(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprInterval(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAll(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprBetween(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprSet(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprRowNumber(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprOver(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprPartition(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprUpdate(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprDelete(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprValues(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprInto(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprInsert(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAsc(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprDesc(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprHaving(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprLimit(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprGroup(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprBy(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprInner(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprOn(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprLike(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAcc(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprGeq(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprLeq(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprDivide(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprStar(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprSub(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprAdd(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprOper(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprEnd(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprElse(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprThen(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprRBrace(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprLBrace(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprIsNull(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprNullIf(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprIf(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprIfNull(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprConcat(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprCoalesce(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprConvert(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprCast(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprChar(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprDate(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateTime(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprSigned(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprInteger(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprDecimal(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprYear(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprStrToDate(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprWeekOfYear(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprWeek(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprTime(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprSecond(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprQuarter(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprNow(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprMonth(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprMinute(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLastDay(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprHour(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprDayOfYear(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprDayOfWeek(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprDay(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateFormat(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateAdd(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateSub(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateDiff(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprCurDate(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprOrder(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprTruncate(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprTan(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprSum(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprSqrt(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprSign(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprRound(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprRand(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprPow(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprPower(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprPi(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprMod(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprMin(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprMax(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLog2(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLog10(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLog(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLn(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprFloor(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprExp(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprCount(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprCot(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprCos(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprCeil(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprCeiling(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprAtan(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprAsin(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprAcos(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprAvg(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprAbs(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprUpper(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprTrim(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprSubStr(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprStrcmp(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprRtrim(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprReverse(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprReplace(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLtrim(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLcase(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLower(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprInstr(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLocate(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprConcatWs(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLen(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprAscii(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprJoin(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprOuter(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprCross(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprRight(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprLeft(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }


    protected Statement exprNeq(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprGt(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprLt(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprLlm(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprRrm(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprIs(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprIn(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprNot(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprExists(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprEq(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprOr(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAnd(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprBitAnd(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprBitOr(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprBitXor(ExprInfo exprInfo) {
        return exprOper(exprInfo);
    }

    protected Statement exprWhere(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprWhen(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprCase(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprMyFunction(ExprInfo exprInfo) {
        return exprFunction(exprInfo);
    }

    protected Statement exprKeyWord(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprFunction(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprDefault(ExprInfo exprInfo) {
        if (self)
            return exprSelf(exprInfo);
        else if (acceptSet.contains(ExprType.NIL)) {
            Statement statement = nil();
            exprReader.pop();
            return statement;
        } else throw new RuntimeException(ExprUtil.wrapper(this, exprReader));
    }

    protected Statement exprSelf(ExprInfo exprInfo) {
        throw new RuntimeException(this.getClass().getSimpleName() + "未实现'" + exprInfo.getExprType() + "'");
    }


    protected Statement exprDistinct(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprFrom(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprSelect(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAs(ExprInfo exprInfo) {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprComma(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprSymbol(ExprInfo exprInfo) {
        return exprDefault(exprInfo);
    }

    protected Statement exprLetter(ExprInfo exprInfo) {
        return exprSymbol(exprInfo);
    }

    protected Statement exprSkip(ExprInfo exprInfo) {
        return exprSymbol(exprInfo);
    }

    protected Statement exprStr(ExprInfo exprInfo) {
        return exprSymbol(exprInfo);
    }

    protected Statement exprFloat(ExprInfo exprInfo) {
        return exprSymbol(exprInfo);
    }

    protected Statement exprInt(ExprInfo exprInfo) {
        return exprSymbol(exprInfo);
    }

    protected Statement exprLong(ExprInfo exprInfo) {
        return exprSymbol(exprInfo);
    }

    protected Statement exprDouble(ExprInfo exprInfo) {
        return exprSymbol(exprInfo);
    }

    protected Statement exprBoolean(ExprInfo exprInfo) {
        return exprSymbol(exprInfo);
    }

    protected abstract Statement nil();

    protected boolean exprBefore(ExprInfo exprInfo) {
        return self = acceptSet.contains(exprInfo.getExprType());
    }

    public SqlExpr setExprTypes(ExprType... exprTypes) {
        acceptSet.clear();
        acceptSet.addAll(Arrays.asList(exprTypes));
        return this;
    }

    protected SqlExpr addExprTypes(ExprType... exprTypes) {
        acceptSet.addAll(Arrays.asList(exprTypes));
        return this;
    }

    public Set<ExprType> getAcceptSet() {
        return acceptSet;
    }

    private boolean tryMark() {
        return exprReader.tryMark(this);
    }

    public boolean isClose() {
        return exprReader.isClose();
    }

}
