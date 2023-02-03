package com.moxa.dream.antlr.util;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.read.ExprReader;

import java.util.Stack;

public class ExprUtil {

    public static boolean isLetter(int c) {
        return 65 <= c && c <= 90 || 97 <= c && c <= 122 || c == 95 || c == 83 || c == 36;
    }

    public static boolean isStr(int c) {
        return c == 39;
    }

    public static boolean isJavaStr(int c) {
        return c == 34;
    }

    public static boolean isSingleMark(int c) {
        return c == 96;
    }

    public static boolean isNumber(int c) {
        return 48 <= c && c <= 57;
    }

    public static boolean isF(int c) {
        return 48 == 70 || c == 102;
    }

    public static boolean isD(int c) {
        return 68 == c || c == 100;
    }

    public static boolean isL(int c) {
        return 76 == c || c == 108;
    }

    public static boolean isDot(int c) {
        return c == 46;
    }

    public static ExprType getExprTypeInLetter(String info) {
        switch (info.toUpperCase().hashCode()) {
            case -1852692228://SELECT
                return ExprType.SELECT;
            case 1071324924://DISTINCT
                return ExprType.DISTINCT;
            case 2166698://FROM
                return ExprType.FROM;
            case 2332679://LEFT
                return ExprType.LEFT;
            case 77974012://RIGHT
                return ExprType.RIGHT;
            case 69817910://INNER
                return ExprType.INNER;
            case 75573339://OUTER
                return ExprType.OUTER;
            case 2282794://JOIN
                return ExprType.JOIN;
            case 2098://AS
                return ExprType.AS;
            case 2527://ON
                return ExprType.ON;
            case 64397344://CROSS
                return ExprType.CROSS;
            case 77491://NOT:
                return ExprType.NOT;
            case 2058938460://EXISTS
                return ExprType.EXISTS;
            case 2341://IN
                return ExprType.IN;
            case 2346://IS
                return ExprType.IS;
            case 2336663://LIKE
                return ExprType.LIKE;
            case 501348328://BETWEEN
                return ExprType.BETWEEN;
            case 2135://BY
                return ExprType.BY;
            case 82560199://WHERE
                return ExprType.WHERE;
            case 68091487://GROUP
                return ExprType.GROUP;
            case -7778060://GROUP_CONCAT
                return ExprType.GROUP_CONCAT;
            case 2123962405://HAVING
                return ExprType.HAVING;
            case 75468590://ORDER
                return ExprType.ORDER;
            case 65105://ASC
                return ExprType.ASC;
            case 2094737://DESC
                return ExprType.DESC;
            case 72438683://LIMIT
                return ExprType.LIMIT;
            case -2130463047://INSERT
                return ExprType.INSERT;
            case 2252384://INTO
                return ExprType.INTO;
            case -1770483422://VALUES
                return ExprType.VALUES;
            case -1785516855://UPDATE
                return ExprType.UPDATE;
            case 81986://SET
                return ExprType.SET;
            case 2012838315://DELETE
                return ExprType.DELETE;
            case 1353045189://INTERVAL
                return ExprType.INTERVAL;
            case 62568241://ASCII
                return ExprType.ASCII;
            case -1089186833://CHAR_LENGTH
                return ExprType.CHAR_LENGTH;
            case -2049076004://CHARACTER_LENGTH
                return ExprType.CHARACTER_LENGTH;
            case 75253://LEN
                return ExprType.LEN;
            case -2053034266://LENGTH
                return ExprType.LENGTH;
            case 1993501460://CONCAT
                return ExprType.CONCAT;
            case 1889287143://CONCAT_WS
                return ExprType.CONCAT_WS;
            case 1184709486://FIND_IN_SET
                return ExprType.FIND_IN_SET;
            case 69823180://INSTR
                return ExprType.INSTR;
            case -2044132526://LOCATE
                return ExprType.LOCATE;
            case 72248700://LCASE
                return ExprType.LCASE;
            case 72626913://LOWER
                return ExprType.LOWER;
            case 72771182://LTRIM
                return ExprType.LTRIM;
            case -1881202277://REPEAT
                return ExprType.REPEAT;
            case 1817829058://REVERSE
                return ExprType.REVERSE;
            case 1812479636://REPLACE
                return ExprType.REPLACE;
            case 78312308://RTRIM
                return ExprType.RTRIM;
            case -1838662283://STRCMP
                return ExprType.STRCMP;
            case -1838199823://SUBSTR
                return ExprType.SUBSTR;
            case -977830351://SUBSTRING
                return ExprType.SUBSTRING;
            case 2583586://TRIM
                return ExprType.TRIM;
            case 80560389://UCASE
                return ExprType.UCASE;
            case 80961666://UPPER
                return ExprType.UPPER;
            case 2343079://LPAD
                return ExprType.LPAD;
            case 2521825://RPAD
                return ExprType.RPAD;
            case 64594://ABS
                return ExprType.ABS;
            case 65202://AVG
                return ExprType.AVG;
            case 2003334://ACOS
                return ExprType.ACOS;
            case 2018519://ASIN
                return ExprType.ASIN;
            case 82104://SIN
                return ExprType.SIN;
            case 2019232://ATAN
                return ExprType.ATAN;
            case 62596242://ATAN2
                return ExprType.ATAN2;
            case 2064645://CEIL
                return ExprType.CEIL;
            case 1378369693://CEILING
                return ExprType.CEILING;
            case 66919://COS
                return ExprType.COS;
            case 66920://COT
                return ExprType.COT;
            case 64313583://COUNT
                return ExprType.COUNT;
            case 69117://EXP
                return ExprType.EXP;
            case 66989036://FLOOR
                return ExprType.FLOOR;
            case 2434://LN
                return ExprType.LN;
            case 75556://LOG
                return ExprType.LOG;
            case 72610883://LOG10
                return ExprType.LOG10;
            case 2342286://LOG2
                return ExprType.LOG2;
            case 67697://DIV
                return ExprType.DIV;
            case 76100://MAX
                return ExprType.MAX;
            case 76338://MIN
                return ExprType.MIN;
            case 76514://MOD
                return ExprType.MOD;
            case 2553://PI
                return ExprType.PI;
            case 79416://POW
                return ExprType.POW;
            case 76320997://POWER
                return ExprType.POWER;
            case 2507813://RAND
                return ExprType.RAND;
            case 78166382://ROUND
                return ExprType.ROUND;
            case 2545085://SIGN
                return ExprType.SIGN;
            case 2553120://SQRT
                return ExprType.SQRT;
            case 82475://SUM
                return ExprType.SUM;
            case 82817://TAN
                return ExprType.TAN;
            case -1659355802://TRUNCATE
                return ExprType.TRUNCATE;
            case -1718300145://DATE_SUB
                return ExprType.DATE_SUB;
            case 1844501966://CURDATE
                return ExprType.CURDATE;
            case -1719114573://DATEDIFF
                return ExprType.DATEDIFF;
            case -1718317968://DATE_ADD
                return ExprType.DATE_ADD;
            case 1458413992://DATE_FORMAT
                return ExprType.DATE_FORMAT;
            case 67452://DAY
                return ExprType.DAY;
            case 1963754477://DAYOFMONTH
                return ExprType.DAYOFMONTH;
            case -1321838393://DAYOFWEEK
                return ExprType.DAYOFWEEK;
            case -1321778928://DAYOFYEAR
                return ExprType.DAYOFYEAR;
            case 2223588://HOUR
                return ExprType.HOUR;
            case -675510701://LAST_DAY
                return ExprType.LAST_DAY;
            case -2020697580://MINUTE
                return ExprType.MINUTE;
            case 73542240://MONTH
                return ExprType.MONTH;
            case 77494://NOW
                return ExprType.NOW;
            case -1019868197://SYSDATE
                return ExprType.SYSDATE;
            case 1369386636://QUARTER
                return ExprType.QUARTER;
            case -1852950412://SECOND
                return ExprType.SECOND;
            case 1617900228://STR_TO_DATE
                return ExprType.STR_TO_DATE;
            case 2575053://TIME
                return ExprType.TIME;
            case 79100134://SPACE
                return ExprType.SPACE;
            case 224457413://SEPARATOR;
                return ExprType.SEPARATOR;
            case 2660340://WEEK
                return ExprType.WEEK;
            case 90085256://WEEKOFYEAR
                return ExprType.WEEKOFYEAR;
            case 2719805://YEAR
                return ExprType.YEAR;
            case 2061104://CASE
                return ExprType.CASE;
            case 1669573011://CONVERT
                return ExprType.CONVERT;
            case 2061119://CAST
                return ExprType.CAST;
            case -1849138404://SIGNED
                return ExprType.SIGNED;
            case 72655://INT
                return ExprType.INT;
            case -1618932450://INTEGER
                return ExprType.INTEGER;
            case 66988604://FLOAT
                return ExprType.FLOAT;
            case 2067286://CHAR
                return ExprType.CHAR;
            case 1534573311://UNIX_TIMESTAMP
                return ExprType.UNIX_TIMESTAMP;
            case 1044724042://FROM_UNIXTIME
                return ExprType.FROM_UNIXTIME;
            case 2090926://DATE
                return ExprType.DATE;
            case -1718637701://DATETIME
                return ExprType.DATETIME;
            case -2034720975://DECIMAL
                return ExprType.DECIMAL;
            case -164257881://COALESCE
                return ExprType.COALESCE;
            case 2663226://WHEN
                return ExprType.WHEN;
            case 2573853://THEN
                return ExprType.THEN;
            case 2131257://ELSE
                return ExprType.ELSE;
            case -2125979215://ISNULL
                return ExprType.ISNULL;
            case -2137984988://IFNULL
                return ExprType.IFNULL;
            case 2333://IF
                return ExprType.IF;
            case -1981054748://NULLIF
                return ExprType.NULLIF;
            case -1966450541://OFFSET
                return ExprType.OFFSET;
            case 80895663://UNION
                return ExprType.UNION;
            case 68795://END
                return ExprType.END;
            case 64897://ALL
                return ExprType.ALL;
            case 64951://AND
                return ExprType.AND;
            case 2531://OR
                return ExprType.OR;
            case 2038860142://ROW_NUMBER
                return ExprType.ROW_NUMBER;
            case 2438356://OVER
                return ExprType.OVER;
            case 986784458://PARTITION
                return ExprType.PARTITION;
            case -407597414://TO_CHAR
                return ExprType.TO_CHAR;
            case -531820147://TO_NUMBER
                return ExprType.TO_NUMBER;
            case -407573774://TO_DATE
                return ExprType.TO_DATE;
            case -914382798://TO_TIMESTAMP
                return ExprType.TO_TIMESTAMP;
            default:
                return ExprType.LETTER;
        }
    }

    public static String wrapper(ExprReader exprReader) {
        Stack<ExprInfo> exprInfoStack = exprReader.getExprInfoStack();
        StringBuilder builder = new StringBuilder();
        builder.append("编译SQL：'" + exprReader.getSql() + "'失败，已解析类型:\n");
        int size = exprInfoStack.size();
        while (!exprInfoStack.isEmpty()) {
            ExprInfo exprInfo = exprInfoStack.pop();
            builder.append((size--) + "\t" + "类型:" + exprInfo.getExprType() + ",字符:" + exprInfo.getInfo() + ",位置:(" + exprInfo.getStart() + "-" + exprInfo.getEnd() + ")\n");
        }
        return builder.toString();
    }

    public static boolean isLBrace(int c) {
        return c == 40;
    }

    public static boolean isRBrace(int c) {
        return c == 41;
    }

    public static boolean isEmpty(String val) {
        return val == null || val.length() == 0;
    }

    public static boolean isBlank(int c) {
        return c == 9 || c == 10 || c == 32;
    }
}
