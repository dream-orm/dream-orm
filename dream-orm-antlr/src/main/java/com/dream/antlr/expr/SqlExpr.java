package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.ExprFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;

import java.util.*;

/**
 * 翻译顶级抽象语法解析类
 */
public abstract class SqlExpr {
    //单词读取流
    protected ExprReader exprReader;
    //本语法器是否可解析单词
    protected boolean self;
    //可解析的单词类型
    protected List<ExprType> acceptList = new ArrayList<>();

    public SqlExpr(ExprReader exprReader) {
        this.exprReader = exprReader;
    }

    /**
     * 读取下一个词的具体信息
     *
     * @return
     * @throws AntlrException
     */
    public ExprInfo push() throws AntlrException {
        ExprInfo exprInfo = exprReader.push();
        exprReader.exprFactory = exprFactory(exprInfo);
        return exprInfo;
    }

    /**
     * 根据当前词，选择对应的语法解析器
     *
     * @return
     * @throws AntlrException
     */
    public Statement expr() throws AntlrException {
        //获取最后读入的单词信息
        ExprInfo exprInfo = exprReader.getLastInfo();
        if (exprInfo == null) {
            exprInfo = push();
        }
        //获取单词类型
        ExprType exprType = exprInfo.getExprType();
        //判断本语法器是否可解析单词
        if (exprBefore(exprType)) {
            return exprReader.exprFactory.apply(this);
        } else {
            return exprNil(exprInfo);
        }
    }

    protected ExprFactory exprFactory(ExprInfo exprInfo) {
        ExprFactory exprFactory;
        switch (exprInfo.getExprType()) {
            case TINYINT:
                exprFactory = sqlExpr -> sqlExpr.exprTinyInt(exprInfo);
                break;
            case SMALLINT:
                exprFactory = sqlExpr -> sqlExpr.exprSmallInt(exprInfo);
                break;
            case MEDIUMINT:
                exprFactory = sqlExpr -> sqlExpr.exprMediumInt(exprInfo);
                break;
            case INT:
                exprFactory = sqlExpr -> sqlExpr.exprInt(exprInfo);
                break;
            case BIGINT:
                exprFactory = sqlExpr -> sqlExpr.exprBigInt(exprInfo);
                break;
            case LONG:
                exprFactory = sqlExpr -> sqlExpr.exprLong(exprInfo);
                break;
            case FLOAT:
                exprFactory = sqlExpr -> sqlExpr.exprFloat(exprInfo);
                break;
            case DOUBLE:
                exprFactory = sqlExpr -> sqlExpr.exprDouble(exprInfo);
                break;
            case DOT:
                exprFactory = sqlExpr -> sqlExpr.exprDot(exprInfo);
                break;
            case STR:
                exprFactory = sqlExpr -> sqlExpr.exprStr(exprInfo);
                break;
            case JAVA_STR:
                exprFactory = sqlExpr -> sqlExpr.exprJavaStr(exprInfo);
                break;
            case LETTER:
                exprFactory = sqlExpr -> sqlExpr.exprLetter(exprInfo);
                break;
            case NUMBER:
                exprFactory = sqlExpr -> sqlExpr.exprNumber(exprInfo);
                break;
            case LIKE:
                exprFactory = sqlExpr -> sqlExpr.exprLike(exprInfo);
                break;
            case BETWEEN:
                exprFactory = sqlExpr -> sqlExpr.exprBetween(exprInfo);
                break;
            case LBRACE:
                exprFactory = sqlExpr -> sqlExpr.exprLBrace(exprInfo);
                break;
            case RBRACE:
                exprFactory = sqlExpr -> sqlExpr.exprRBrace(exprInfo);
                break;
            case INTERVAL:
                exprFactory = sqlExpr -> sqlExpr.exprInterval(exprInfo);
                break;
            case COMMA:
                exprFactory = sqlExpr -> sqlExpr.exprComma(exprInfo);
                break;
            case SINGLE_MARK:
                exprFactory = sqlExpr -> sqlExpr.exprSingleMark(exprInfo);
                break;
            case MARK:
                exprFactory = sqlExpr -> sqlExpr.exprMark(exprInfo);
                break;
            case ON:
                exprFactory = sqlExpr -> sqlExpr.exprOn(exprInfo);
                break;
            case AS:
                exprFactory = sqlExpr -> sqlExpr.exprAs(exprInfo);
                break;
            case ASC:
                exprFactory = sqlExpr -> sqlExpr.exprAsc(exprInfo);
                break;
            case DESC:
                exprFactory = sqlExpr -> sqlExpr.exprDesc(exprInfo);
                break;
            case ASCII:
                exprFactory = sqlExpr -> sqlExpr.exprAscii(exprInfo);
                break;
            case CHARACTER_LENGTH:
            case CHAR_LENGTH:
                exprFactory = sqlExpr -> sqlExpr.exprCharLength(exprInfo);
                break;
            case LENGTH:
                exprFactory = sqlExpr -> sqlExpr.exprLength(exprInfo);
                break;
            case CONCAT_WS:
                exprFactory = sqlExpr -> sqlExpr.exprConcatWs(exprInfo);
                break;
            case INSTR:
                exprFactory = sqlExpr -> sqlExpr.exprInstr(exprInfo);
                break;
            case LOCATE:
                exprFactory = sqlExpr -> sqlExpr.exprLocate(exprInfo);
                break;
            case LCASE:
                exprFactory = sqlExpr -> sqlExpr.exprLcase(exprInfo);
                break;
            case LOWER:
                exprFactory = sqlExpr -> sqlExpr.exprLower(exprInfo);
                break;
            case LTRIM:
                exprFactory = sqlExpr -> sqlExpr.exprLtrim(exprInfo);
                break;
            case REPEAT:
                exprFactory = sqlExpr -> sqlExpr.exprRepeat(exprInfo);
                break;
            case REVERSE:
                exprFactory = sqlExpr -> sqlExpr.exprReverse(exprInfo);
                break;
            case REPLACE:
                exprFactory = sqlExpr -> sqlExpr.exprReplace(exprInfo);
                break;
            case RTRIM:
                exprFactory = sqlExpr -> sqlExpr.exprRtrim(exprInfo);
                break;
            case STRCMP:
                exprFactory = sqlExpr -> sqlExpr.exprStrcmp(exprInfo);
                break;
            case SEPARATOR:
                exprFactory = sqlExpr -> sqlExpr.exprSeparator(exprInfo);
                break;
            case SUBSTR:
            case SUBSTRING:
                exprFactory = sqlExpr -> sqlExpr.exprSubStr(exprInfo);
                break;
            case SPACE:
                exprFactory = sqlExpr -> sqlExpr.exprSpace(exprInfo);
                break;
            case TRIM:
                exprFactory = sqlExpr -> sqlExpr.exprTrim(exprInfo);
                break;
            case UCASE:
            case UPPER:
                exprFactory = sqlExpr -> sqlExpr.exprUpper(exprInfo);
                break;
            case LPAD:
                exprFactory = sqlExpr -> sqlExpr.exprLpad(exprInfo);
                break;
            case RPAD:
                exprFactory = sqlExpr -> sqlExpr.exprRpad(exprInfo);
                break;
            case DATABASE:
                exprFactory = sqlExpr -> sqlExpr.exprDatabase(exprInfo);
                break;
            case TABLE:
                exprFactory = sqlExpr -> sqlExpr.exprTable(exprInfo);
                break;
            case ABS:
                exprFactory = sqlExpr -> sqlExpr.exprAbs(exprInfo);
                break;
            case AVG:
                exprFactory = sqlExpr -> sqlExpr.exprAvg(exprInfo);
                break;
            case ACOS:
                exprFactory = sqlExpr -> sqlExpr.exprAcos(exprInfo);
                break;
            case ASIN:
                exprFactory = sqlExpr -> sqlExpr.exprAsin(exprInfo);
                break;
            case SIN:
                exprFactory = sqlExpr -> sqlExpr.exprSin(exprInfo);
                break;
            case ATAN:
                exprFactory = sqlExpr -> sqlExpr.exprAtan(exprInfo);
                break;
            case ALL:
                exprFactory = sqlExpr -> sqlExpr.exprAll(exprInfo);
                break;
            case CEIL:
                exprFactory = sqlExpr -> sqlExpr.exprCeil(exprInfo);
                break;
            case CEILING:
                exprFactory = sqlExpr -> sqlExpr.exprCeiling(exprInfo);
                break;
            case COS:
                exprFactory = sqlExpr -> sqlExpr.exprCos(exprInfo);
                break;
            case COT:
                exprFactory = sqlExpr -> sqlExpr.exprCot(exprInfo);
                break;
            case COUNT:
                exprFactory = sqlExpr -> sqlExpr.exprCount(exprInfo);
                break;
            case EXP:
                exprFactory = sqlExpr -> sqlExpr.exprExp(exprInfo);
                break;
            case FLOOR:
                exprFactory = sqlExpr -> sqlExpr.exprFloor(exprInfo);
                break;
            case LN:
                exprFactory = sqlExpr -> sqlExpr.exprLn(exprInfo);
                break;
            case LOG:
                exprFactory = sqlExpr -> sqlExpr.exprLog(exprInfo);
                break;
            case LOG10:
                exprFactory = sqlExpr -> sqlExpr.exprLog10(exprInfo);
                break;
            case LOG2:
                exprFactory = sqlExpr -> sqlExpr.exprLog2(exprInfo);
                break;
            case MAX:
                exprFactory = sqlExpr -> sqlExpr.exprMax(exprInfo);
                break;
            case MIN:
                exprFactory = sqlExpr -> sqlExpr.exprMin(exprInfo);
                break;
            case PI:
                exprFactory = sqlExpr -> sqlExpr.exprPi(exprInfo);
                break;
            case POW:
                exprFactory = sqlExpr -> sqlExpr.exprPow(exprInfo);
                break;
            case POWER:
                exprFactory = sqlExpr -> sqlExpr.exprPower(exprInfo);
                break;
            case RAND:
                exprFactory = sqlExpr -> sqlExpr.exprRand(exprInfo);
                break;
            case ROUND:
                exprFactory = sqlExpr -> sqlExpr.exprRound(exprInfo);
                break;
            case SIGN:
                exprFactory = sqlExpr -> sqlExpr.exprSign(exprInfo);
                break;
            case SQRT:
                exprFactory = sqlExpr -> sqlExpr.exprSqrt(exprInfo);
                break;
            case SUM:
                exprFactory = sqlExpr -> sqlExpr.exprSum(exprInfo);
                break;
            case TAN:
                exprFactory = sqlExpr -> sqlExpr.exprTan(exprInfo);
                break;
            case CREATE:
                exprFactory = sqlExpr -> sqlExpr.exprCreate(exprInfo);
                break;
            case ALTER:
                exprFactory = sqlExpr -> sqlExpr.exprAlter(exprInfo);
                break;
            case TRUNCATE:
                exprFactory = sqlExpr -> sqlExpr.exprTruncate(exprInfo);
                break;
            case DROP:
                exprFactory = sqlExpr -> sqlExpr.exprDrop(exprInfo);
                break;
            case COLUMN:
                exprFactory = sqlExpr -> sqlExpr.exprColumn(exprInfo);
                break;
            case RENAME:
                exprFactory = sqlExpr -> sqlExpr.exprRename(exprInfo);
                break;
            case TO:
                exprFactory = sqlExpr -> sqlExpr.exprTo(exprInfo);
                break;
            case MODIFY:
                exprFactory = sqlExpr -> sqlExpr.exprModify(exprInfo);
                break;
            case ORDER:
                exprFactory = sqlExpr -> sqlExpr.exprOrder(exprInfo);
                break;
            case CURDATE:
                exprFactory = sqlExpr -> sqlExpr.exprCurDate(exprInfo);
                break;
            case DATEDIFF:
                exprFactory = sqlExpr -> sqlExpr.exprDateDiff(exprInfo);
                break;
            case DATE_ADD:
                exprFactory = sqlExpr -> sqlExpr.exprDateAdd(exprInfo);
                break;
            case DATE_SUB:
                exprFactory = sqlExpr -> sqlExpr.exprDateSub(exprInfo);
                break;
            case DATE_FORMAT:
                exprFactory = sqlExpr -> sqlExpr.exprDateFormat(exprInfo);
                break;
            case EXTRACT:
                exprFactory = sqlExpr -> sqlExpr.exprExtract(exprInfo);
                break;
            case DAY:
            case DAYOFMONTH:
                exprFactory = sqlExpr -> sqlExpr.exprDay(exprInfo);
                break;
            case DAYOFWEEK:
                exprFactory = sqlExpr -> sqlExpr.exprDayOfWeek(exprInfo);
                break;
            case DAYOFYEAR:
                exprFactory = sqlExpr -> sqlExpr.exprDayOfYear(exprInfo);
                break;
            case HOUR:
                exprFactory = sqlExpr -> sqlExpr.exprHour(exprInfo);
                break;
            case LAST_DAY:
                exprFactory = sqlExpr -> sqlExpr.exprLastDay(exprInfo);
                break;
            case MINUTE:
                exprFactory = sqlExpr -> sqlExpr.exprMinute(exprInfo);
                break;
            case MONTH:
                exprFactory = sqlExpr -> sqlExpr.exprMonth(exprInfo);
                break;
            case NOW:
                exprFactory = sqlExpr -> sqlExpr.exprNow(exprInfo);
                break;
            case SYSDATE:
                exprFactory = sqlExpr -> sqlExpr.exprSysDate(exprInfo);
                break;
            case QUARTER:
                exprFactory = sqlExpr -> sqlExpr.exprQuarter(exprInfo);
                break;
            case SECOND:
                exprFactory = sqlExpr -> sqlExpr.exprSecond(exprInfo);
                break;
            case TIME:
                exprFactory = sqlExpr -> sqlExpr.exprTime(exprInfo);
                break;
            case WEEK:
                exprFactory = sqlExpr -> sqlExpr.exprWeek(exprInfo);
                break;
            case WEEKOFYEAR:
                exprFactory = sqlExpr -> sqlExpr.exprWeekOfYear(exprInfo);
                break;
            case YEAR:
                exprFactory = sqlExpr -> sqlExpr.exprYear(exprInfo);
                break;
            case STR_TO_DATE:
                exprFactory = sqlExpr -> sqlExpr.exprStrToDate(exprInfo);
                break;
            case MY_FUNCTION:
                exprFactory = sqlExpr -> sqlExpr.exprMyFunction(exprInfo);
                break;
            case CONVERT:
                exprFactory = sqlExpr -> sqlExpr.exprConvert(exprInfo);
                break;
            case CAST:
                exprFactory = sqlExpr -> sqlExpr.exprCast(exprInfo);
                break;
            case CHAR:
                exprFactory = sqlExpr -> sqlExpr.exprChar(exprInfo);
                break;
            case VARCHAR:
                exprFactory = sqlExpr -> sqlExpr.exprVarChar(exprInfo);
                break;
            case TEXT:
                exprFactory = sqlExpr -> sqlExpr.exprText(exprInfo);
                break;
            case BLOB:
                exprFactory = sqlExpr -> sqlExpr.exprBlob(exprInfo);
                break;
            case UNIX_TIMESTAMP:
                exprFactory = sqlExpr -> sqlExpr.exprUnixTimeStamp(exprInfo);
                break;
            case FROM_UNIXTIME:
                exprFactory = sqlExpr -> sqlExpr.exprFromUnixTime(exprInfo);
                break;
            case DATE:
                exprFactory = sqlExpr -> sqlExpr.exprDate(exprInfo);
                break;
            case DATETIME:
                exprFactory = sqlExpr -> sqlExpr.exprDateTime(exprInfo);
                break;
            case TIMESTAMP:
                exprFactory = sqlExpr -> sqlExpr.exprTimeStamp(exprInfo);
                break;
            case SIGNED:
                exprFactory = sqlExpr -> sqlExpr.exprSigned(exprInfo);
                break;
            case INTEGER:
                exprFactory = sqlExpr -> sqlExpr.exprInteger(exprInfo);
                break;
            case DECIMAL:
                exprFactory = sqlExpr -> sqlExpr.exprDecimal(exprInfo);
                break;
            case COALESCE:
                exprFactory = sqlExpr -> sqlExpr.exprCoalesce(exprInfo);
                break;
            case CONCAT:
                exprFactory = sqlExpr -> sqlExpr.exprConcat(exprInfo);
                break;
            case GROUP_CONCAT:
                exprFactory = sqlExpr -> sqlExpr.exprGroupConcat(exprInfo);
                break;
            case FIND_IN_SET:
                exprFactory = sqlExpr -> sqlExpr.exprFindInSet(exprInfo);
                break;
            case IFNULL:
                exprFactory = sqlExpr -> sqlExpr.exprIfNull(exprInfo);
                break;
            case IF:
                exprFactory = sqlExpr -> sqlExpr.exprIf(exprInfo);
                break;
            case NULLIF:
                exprFactory = sqlExpr -> sqlExpr.exprNullIf(exprInfo);
                break;
            case OFFSET:
                exprFactory = sqlExpr -> sqlExpr.exprOffSet(exprInfo);
                break;
            case UNION:
                exprFactory = sqlExpr -> sqlExpr.exprUnion(exprInfo);
                break;
            case FOR:
                exprFactory = sqlExpr -> sqlExpr.exprFor(exprInfo);
                break;
            case NOWAIT:
                exprFactory = sqlExpr -> sqlExpr.exprNoWait(exprInfo);
                break;
            case ISNULL:
                exprFactory = sqlExpr -> sqlExpr.exprIsNull(exprInfo);
                break;
            case CASE:
                exprFactory = sqlExpr -> sqlExpr.exprCase(exprInfo);
                break;
            case WHEN:
                exprFactory = sqlExpr -> sqlExpr.exprWhen(exprInfo);
                break;
            case THEN:
                exprFactory = sqlExpr -> sqlExpr.exprThen(exprInfo);
                break;
            case ELSE:
                exprFactory = sqlExpr -> sqlExpr.exprElse(exprInfo);
                break;
            case END:
                exprFactory = sqlExpr -> sqlExpr.exprEnd(exprInfo);
                break;
            case SELECT:
                exprFactory = sqlExpr -> sqlExpr.exprSelect(exprInfo);
                break;
            case FROM:
                exprFactory = sqlExpr -> sqlExpr.exprFrom(exprInfo);
                break;
            case WHERE:
                exprFactory = sqlExpr -> sqlExpr.exprWhere(exprInfo);
                break;
            case HAVING:
                exprFactory = sqlExpr -> sqlExpr.exprHaving(exprInfo);
                break;
            case LIMIT:
                exprFactory = sqlExpr -> sqlExpr.exprLimit(exprInfo);
                break;
            case DISTINCT:
                exprFactory = sqlExpr -> sqlExpr.exprDistinct(exprInfo);
                break;
            case AND:
                exprFactory = sqlExpr -> sqlExpr.exprAnd(exprInfo);
                break;
            case BITAND://&
                exprFactory = sqlExpr -> sqlExpr.exprBitAnd(exprInfo);
                break;
            case BITOR://|
                exprFactory = sqlExpr -> sqlExpr.exprBitOr(exprInfo);
                break;
            case BITXOR://^
                exprFactory = sqlExpr -> sqlExpr.exprBitXor(exprInfo);
                break;
            case OR:
                exprFactory = sqlExpr -> sqlExpr.exprOr(exprInfo);
                break;
            case EQ:
                exprFactory = sqlExpr -> sqlExpr.exprEq(exprInfo);
                break;
            case IS:
                exprFactory = sqlExpr -> sqlExpr.exprIs(exprInfo);
                break;
            case IN:
                exprFactory = sqlExpr -> sqlExpr.exprIn(exprInfo);
                break;
            case NOT:
                exprFactory = sqlExpr -> sqlExpr.exprNot(exprInfo);
                break;
            case NULL:
                exprFactory = sqlExpr -> sqlExpr.exprNull(exprInfo);
                break;
            case EXISTS:
                exprFactory = sqlExpr -> sqlExpr.exprExists(exprInfo);
                break;
            case LT:
                exprFactory = sqlExpr -> sqlExpr.exprLt(exprInfo);
                break;
            case LLM:
                exprFactory = sqlExpr -> sqlExpr.exprLlm(exprInfo);
                break;
            case RRM:
                exprFactory = sqlExpr -> sqlExpr.exprRrm(exprInfo);
                break;
            case GT:
                exprFactory = sqlExpr -> sqlExpr.exprGt(exprInfo);
                break;
            case LEQ:
                exprFactory = sqlExpr -> sqlExpr.exprLeq(exprInfo);
                break;
            case GEQ:
                exprFactory = sqlExpr -> sqlExpr.exprGeq(exprInfo);
                break;
            case NEQ:
                exprFactory = sqlExpr -> sqlExpr.exprNeq(exprInfo);
                break;
            case LEFT:
                exprFactory = sqlExpr -> sqlExpr.exprLeft(exprInfo);
                break;
            case GROUP:
                exprFactory = sqlExpr -> sqlExpr.exprGroup(exprInfo);
                break;
            case BY:
                exprFactory = sqlExpr -> sqlExpr.exprBy(exprInfo);
                break;
            case INNER:
                exprFactory = sqlExpr -> sqlExpr.exprInner(exprInfo);
                break;
            case RIGHT:
                exprFactory = sqlExpr -> sqlExpr.exprRight(exprInfo);
                break;
            case CROSS:
                exprFactory = sqlExpr -> sqlExpr.exprCross(exprInfo);
                break;
            case OUTER:
                exprFactory = sqlExpr -> sqlExpr.exprOuter(exprInfo);
                break;
            case JOIN:
                exprFactory = sqlExpr -> sqlExpr.exprJoin(exprInfo);
                break;
            case ADD:
                exprFactory = sqlExpr -> sqlExpr.exprAdd(exprInfo);
                break;
            case SUB:
                exprFactory = sqlExpr -> sqlExpr.exprSub(exprInfo);
                break;
            case STAR:
                exprFactory = sqlExpr -> sqlExpr.exprStar(exprInfo);
                break;
            case DIVIDE:
                exprFactory = sqlExpr -> sqlExpr.exprDivide(exprInfo);
                break;
            case MOD:
                exprFactory = sqlExpr -> sqlExpr.exprMod(exprInfo);
                break;
            case INSERT:
                exprFactory = sqlExpr -> sqlExpr.exprInsert(exprInfo);
                break;
            case INTO:
                exprFactory = sqlExpr -> sqlExpr.exprInto(exprInfo);
                break;
            case VALUES:
                exprFactory = sqlExpr -> sqlExpr.exprValues(exprInfo);
                break;
            case DELETE:
                exprFactory = sqlExpr -> sqlExpr.exprDelete(exprInfo);
                break;
            case UPDATE:
                exprFactory = sqlExpr -> sqlExpr.exprUpdate(exprInfo);
                break;
            case SET:
                exprFactory = sqlExpr -> sqlExpr.exprSet(exprInfo);
                break;
            case ROW_NUMBER:
                exprFactory = sqlExpr -> sqlExpr.exprRowNumber(exprInfo);
                break;
            case OVER:
                exprFactory = sqlExpr -> sqlExpr.exprOver(exprInfo);
                break;
            case PARTITION:
                exprFactory = sqlExpr -> sqlExpr.exprPartition(exprInfo);
                break;
            case INVOKER:
                exprFactory = sqlExpr -> sqlExpr.exprInvoker(exprInfo);
                break;
            case COLON:
                exprFactory = sqlExpr -> sqlExpr.exprColon(exprInfo);
                break;
            case DOLLAR:
                exprFactory = sqlExpr -> sqlExpr.exprDollar(exprInfo);
                break;
            case SHARP:
                exprFactory = sqlExpr -> sqlExpr.exprSharp(exprInfo);
                break;
            case TO_CHAR:
                exprFactory = sqlExpr -> sqlExpr.exprToChar(exprInfo);
                break;
            case TO_NUMBER:
                exprFactory = sqlExpr -> sqlExpr.exprToNumber(exprInfo);
                break;
            case TO_DATE:
                exprFactory = sqlExpr -> sqlExpr.exprToDate(exprInfo);
                break;
            case TO_TIMESTAMP:
                exprFactory = sqlExpr -> sqlExpr.exprToTimeStamp(exprInfo);
                break;
            case AUTO_INCREMENT:
                exprFactory = sqlExpr -> sqlExpr.exprAutoIncrement(exprInfo);
                break;
            case CONSTRAINT:
                exprFactory = sqlExpr -> sqlExpr.exprConstraint(exprInfo);
                break;
            case PRIMARY:
                exprFactory = sqlExpr -> sqlExpr.exprPrimary(exprInfo);
                break;
            case FOREIGN:
                exprFactory = sqlExpr -> sqlExpr.exprForeign(exprInfo);
                break;
            case REFERENCES:
                exprFactory = sqlExpr -> sqlExpr.exprReferences(exprInfo);
                break;
            case KEY:
                exprFactory = sqlExpr -> sqlExpr.exprKey(exprInfo);
                break;
            case ENGINE:
                exprFactory = sqlExpr -> sqlExpr.exprEngine(exprInfo);
                break;
            case CHARSET:
                exprFactory = sqlExpr -> sqlExpr.exprCharset(exprInfo);
                break;
            case COMMENT:
                exprFactory = sqlExpr -> sqlExpr.exprComment(exprInfo);
                break;
            case DEFAULT:
                exprFactory = sqlExpr -> sqlExpr.exprDefault(exprInfo);
                break;
            case ACC:
                exprFactory = sqlExpr -> sqlExpr.exprAcc(exprInfo);
                break;
            default:
                exprFactory = sqlExpr -> sqlExpr.exprNil(exprInfo);
                break;
        }
        return exprFactory;
    }

    protected Statement exprSingleMark(ExprInfo exprInfo) throws AntlrException {
        return exprSymbol(exprInfo);
    }

    protected Statement exprMark(ExprInfo exprInfo) throws AntlrException {
        return exprSymbol(exprInfo);
    }

    protected Statement exprUnion(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprFor(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprNoWait(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprOffSet(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAutoIncrement(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprConstraint(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprPrimary(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprForeign(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprReferences(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprKey(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprEngine(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprCharset(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprComment(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprDefault(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprInvoker(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprColon(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprDollar(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprSharp(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprRepeat(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprSin(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprSpace(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLpad(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprRpad(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDatabase(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprTable(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprInterval(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAll(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprBetween(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprSet(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprRowNumber(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprOver(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprPartition(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprUpdate(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprDelete(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprValues(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprInto(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprInsert(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAsc(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprDesc(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprHaving(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprLimit(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprGroup(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprBy(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprInner(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprOn(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprLike(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAcc(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprGeq(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprLeq(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprDivide(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprStar(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprSub(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprAdd(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprOper(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprEnd(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprElse(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprThen(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprIsNull(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprNullIf(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprIf(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprIfNull(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprConcat(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprGroupConcat(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprFindInSet(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCoalesce(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprConvert(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCast(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprChar(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprVarChar(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprText(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprBlob(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprUnixTimeStamp(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprFromUnixTime(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDate(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateTime(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprTimeStamp(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprSigned(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprInteger(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprDecimal(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprYear(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprStrToDate(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprWeekOfYear(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprWeek(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprTime(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprSecond(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprQuarter(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprNow(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprSysDate(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprMonth(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprMinute(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLastDay(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprHour(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDayOfYear(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDayOfWeek(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDay(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateFormat(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprExtract(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateAdd(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateSub(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDateDiff(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCurDate(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprOrder(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCreate(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAlter(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprTruncate(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprDrop(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprColumn(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprRename(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprTo(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprModify(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprTan(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprSum(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprSqrt(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprSign(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprRound(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprRand(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprPow(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprPower(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprPi(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprMod(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprMin(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprMax(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLog2(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLog10(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLog(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLn(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprFloor(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprExp(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCount(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCot(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCos(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCeil(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCeiling(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprAtan(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprAsin(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprAcos(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprAvg(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprAbs(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprUpper(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprTrim(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprSubStr(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprStrcmp(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprSeparator(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprRtrim(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprReverse(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprReplace(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLtrim(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLcase(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLower(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprInstr(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLocate(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprConcatWs(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprCharLength(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLength(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprAscii(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprJoin(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprOuter(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprCross(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprRight(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprLeft(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }


    protected Statement exprNeq(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprGt(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprLt(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprLlm(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprRrm(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprIs(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprIn(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprNot(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprNull(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprExists(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprEq(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprOr(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAnd(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprBitAnd(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprBitOr(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprBitXor(ExprInfo exprInfo) throws AntlrException {
        return exprOper(exprInfo);
    }

    protected Statement exprWhere(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprWhen(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprCase(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprMyFunction(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprKeyWord(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprFunction(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    /**
     * 规约，如果本语法器可以解析单词，调用解析单词函数，
     * 否则如果本次可以规约，则进行规约，
     * 否则抛异常
     *
     * @param exprInfo 单词信息
     * @return 抽象树
     * @throws AntlrException
     */
    protected Statement exprNil(ExprInfo exprInfo) throws AntlrException {
        if (self) {
            return exprSelf(exprInfo);
        } else if (acceptList.lastIndexOf(ExprType.NIL) >= 0) {
            Statement statement = nil();
            return statement;
        } else {
            Set<ExprType> exprTypeSet = new HashSet<>(acceptList);
            if (exprTypeSet.contains(ExprType.HELP)) {
                SqlExpr sqlExpr = this;
                while (sqlExpr instanceof HelperExpr) {
                    HelperExpr helperExpr = (HelperExpr) sqlExpr;
                    exprTypeSet.addAll(helperExpr.acceptList);
                    sqlExpr = helperExpr.helper.helper();
                }
                exprTypeSet.remove(ExprType.HELP);
            }
            throw new AntlrException("未正确识别:\n单词：" + exprInfo.getInfo() + "\n类型：" + exprInfo.getExprType() + "\n语法：" + this.getClass().getName() + "\n可识别类型：" + exprTypeSet);
        }
    }

    protected Statement exprSelf(ExprInfo exprInfo) throws AntlrException {
        throw new AntlrException(this.getClass().getSimpleName() + "未实现'" + exprInfo.getExprType() + "'");
    }


    protected Statement exprDistinct(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprFrom(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprAs(ExprInfo exprInfo) throws AntlrException {
        return exprKeyWord(exprInfo);
    }

    protected Statement exprComma(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprSymbol(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprLetter(ExprInfo exprInfo) throws AntlrException {
        return exprSymbol(exprInfo);
    }

    protected Statement exprNumber(ExprInfo exprInfo) throws AntlrException {
        return exprSymbol(exprInfo);
    }

    protected Statement exprStr(ExprInfo exprInfo) throws AntlrException {
        return exprSymbol(exprInfo);
    }

    protected Statement exprJavaStr(ExprInfo exprInfo) throws AntlrException {
        return exprSymbol(exprInfo);
    }

    protected Statement exprFloat(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprTinyInt(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprSmallInt(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprMediumInt(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprInt(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprBigInt(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprLong(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprDouble(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprDot(ExprInfo exprInfo) throws AntlrException {
        return exprNil(exprInfo);
    }

    protected Statement exprToChar(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprToNumber(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprToDate(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected Statement exprToTimeStamp(ExprInfo exprInfo) throws AntlrException {
        return exprFunction(exprInfo);
    }

    protected abstract Statement nil();

    protected boolean exprBefore(ExprType exprType) {
        return self = acceptList.contains(exprType);
    }

    public SqlExpr setExprTypes(ExprType... exprTypes) {
        if (!acceptList.isEmpty()) {
            acceptList.clear();
        }
        acceptList.addAll(Arrays.asList(exprTypes));
        return this;
    }

    protected SqlExpr addExprTypes(ExprType... exprTypes) {
        acceptList.addAll(Arrays.asList(exprTypes));
        return this;
    }

    protected String name() {
        return this.getClass().getSimpleName();
    }
}
