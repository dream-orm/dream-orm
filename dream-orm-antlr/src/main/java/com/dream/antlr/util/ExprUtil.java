package com.dream.antlr.util;

import com.dream.antlr.config.ExprType;

public class ExprUtil {
    public static boolean isLetter(int c) {
        return 65 <= c && c <= 90 || 97 <= c && c <= 122 || c == 95 || c == 83 || c >= 19968;
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

    public static boolean isDot(int c) {
        return c == 46;
    }

    public static ExprType getExprTypeInLetter(String info) {
        switch (info.toUpperCase().hashCode()) {
            case -1852692228:
                return ExprType.SELECT;
            case 1071324924:
                return ExprType.DISTINCT;
            case 2166698:
                return ExprType.FROM;
            case 2332679:
                return ExprType.LEFT;
            case 77974012:
                return ExprType.RIGHT;
            case 2169487:
                return ExprType.FULL;
            case 69817910:
                return ExprType.INNER;
            case 75573339:
                return ExprType.OUTER;
            case 2282794:
                return ExprType.JOIN;
            case 2098:
                return ExprType.AS;
            case 2527:
                return ExprType.ON;
            case 64397344:
                return ExprType.CROSS;
            case 77491:
                return ExprType.NOT;
            case 2407815:
                return ExprType.NULL;
            case 2058938460:
                return ExprType.EXISTS;
            case 2341:
                return ExprType.IN;
            case 2346:
                return ExprType.IS;
            case 2336663:
                return ExprType.LIKE;
            case 501348328:
                return ExprType.BETWEEN;
            case 2135:
                return ExprType.BY;
            case 82560199:
                return ExprType.WHERE;
            case 68091487:
                return ExprType.GROUP;
            case -7778060:
                return ExprType.GROUP_CONCAT;
            case 2123962405:
                return ExprType.HAVING;
            case 75468590:
                return ExprType.ORDER;
            case 65105:
                return ExprType.ASC;
            case 2094737:
                return ExprType.DESC;
            case 72438683:
                return ExprType.LIMIT;
            case -2130463047:
                return ExprType.INSERT;
            case 2252384:
                return ExprType.INTO;
            case -1770483422:
                return ExprType.VALUES;
            case -1785516855:
                return ExprType.UPDATE;
            case 81986:
                return ExprType.SET;
            case 2012838315:
                return ExprType.DELETE;
            case 1353045189:
                return ExprType.INTERVAL;
            case 62568241:
                return ExprType.ASCII;
            case -1089186833:
                return ExprType.CHAR_LENGTH;
            case -2049076004:
                return ExprType.CHARACTER_LENGTH;
            case -2053034266:
                return ExprType.LENGTH;
            case 1993501460:
                return ExprType.CONCAT;
            case 1889287143:
                return ExprType.CONCAT_WS;
            case 1184709486:
                return ExprType.FIND_IN_SET;
            case 69823180:
                return ExprType.INSTR;
            case -2044132526:
                return ExprType.LOCATE;
            case 72248700:
                return ExprType.LCASE;
            case 72626913:
                return ExprType.LOWER;
            case 72771182:
                return ExprType.LTRIM;
            case -1881202277:
                return ExprType.REPEAT;
            case 1817829058:
                return ExprType.REVERSE;
            case 1812479636:
                return ExprType.REPLACE;
            case 78312308:
                return ExprType.RTRIM;
            case -1838662283:
                return ExprType.STRCMP;
            case -1838199823:
                return ExprType.SUBSTR;
            case -977830351:
                return ExprType.SUBSTRING;
            case 2583586:
                return ExprType.TRIM;
            case 80560389:
                return ExprType.UCASE;
            case 80961666:
                return ExprType.UPPER;
            case 2343079:
                return ExprType.LPAD;
            case 2521825:
                return ExprType.RPAD;
            case 64594:
                return ExprType.ABS;
            case 65202:
                return ExprType.AVG;
            case 2003334:
                return ExprType.ACOS;
            case 2018519:
                return ExprType.ASIN;
            case 82104:
                return ExprType.SIN;
            case 2019232:
                return ExprType.ATAN;
            case 62596242:
                return ExprType.ATAN2;
            case 2064645:
                return ExprType.CEIL;
            case 1378369693:
                return ExprType.CEILING;
            case 66919:
                return ExprType.COS;
            case 66920:
                return ExprType.COT;
            case 64313583:
                return ExprType.COUNT;
            case 69117:
                return ExprType.EXP;
            case 66989036:
                return ExprType.FLOOR;
            case 2434:
                return ExprType.LN;
            case 75556:
                return ExprType.LOG;
            case 72610883:
                return ExprType.LOG10;
            case 2342286:
                return ExprType.LOG2;
            case 67697:
                return ExprType.DIV;
            case 76100:
                return ExprType.MAX;
            case 76338:
                return ExprType.MIN;
            case 76514:
                return ExprType.MOD;
            case 2553:
                return ExprType.PI;
            case 79416:
                return ExprType.POW;
            case 76320997:
                return ExprType.POWER;
            case 2507813:
                return ExprType.RAND;
            case 78166382:
                return ExprType.ROUND;
            case 2545085:
                return ExprType.SIGN;
            case 2553120:
                return ExprType.SQRT;
            case 82475:
                return ExprType.SUM;
            case 82817:
                return ExprType.TAN;
            case -1659355802:
                return ExprType.TRUNCATE;
            case -1718300145:
                return ExprType.DATE_SUB;
            case 1844501966:
                return ExprType.CURDATE;
            case -479705388:
                return ExprType.CURRENT_DATE;
            case 1844986093:
                return ExprType.CURTIME;
            case -479221261:
                return ExprType.CURRENT_TIME;
            case -1719114573:
                return ExprType.DATEDIFF;
            case -1718317968:
                return ExprType.DATE_ADD;
            case 1458413992:
                return ExprType.DATE_FORMAT;
            case -587306911:
                return ExprType.EXTRACT;
            case 67452:
                return ExprType.DAY;
            case 1963754477:
                return ExprType.DAYOFMONTH;
            case -1321838393:
                return ExprType.DAYOFWEEK;
            case -1321778928:
                return ExprType.DAYOFYEAR;
            case 2223588:
                return ExprType.HOUR;
            case -675510701:
                return ExprType.LAST_DAY;
            case -2020697580:
                return ExprType.MINUTE;
            case 73542240:
                return ExprType.MONTH;
            case 77494:
                return ExprType.NOW;
            case -1019868197:
                return ExprType.SYSDATE;
            case 1369386636:
                return ExprType.QUARTER;
            case -1852950412:
                return ExprType.SECOND;
            case 1617900228:
                return ExprType.STR_TO_DATE;
            case 2575053:
                return ExprType.TIME;
            case 79100134:
                return ExprType.SPACE;
            case 224457413:
                return ExprType.SEPARATOR;
            case 2660340:
                return ExprType.WEEK;
            case 90085256:
                return ExprType.WEEKOFYEAR;
            case 2719805:
                return ExprType.YEAR;
            case 2061104:
                return ExprType.CASE;
            case 1669573011:
                return ExprType.CONVERT;
            case 2061119:
                return ExprType.CAST;
            case -1849138404:
                return ExprType.SIGNED;
            case 72655:
                return ExprType.INT;
            case -1618932450:
                return ExprType.INTEGER;
            case 66988604:
                return ExprType.FLOAT;
            case 2022338513:
                return ExprType.DOUBLE;
            case 2067286:
                return ExprType.CHAR;
            case 1534573311:
                return ExprType.UNIX_TIMESTAMP;
            case 1044724042:
                return ExprType.FROM_UNIXTIME;
            case 2090926:
                return ExprType.DATE;
            case -1718637701:
                return ExprType.DATETIME;
            case -2034720975:
                return ExprType.DECIMAL;
            case -164257881:
                return ExprType.COALESCE;
            case 2663226:
                return ExprType.WHEN;
            case 2573853:
                return ExprType.THEN;
            case 2131257:
                return ExprType.ELSE;
            case -2125979215:
                return ExprType.ISNULL;
            case -2137984988:
                return ExprType.IFNULL;
            case 2333:
                return ExprType.IF;
            case -1981054748:
                return ExprType.NULLIF;
            case -1966450541:
                return ExprType.OFFSET;
            case 80895663:
                return ExprType.UNION;
            case 69801:
                return ExprType.FOR;
            case -1986278730:
                return ExprType.NOWAIT;
            case 68795:
                return ExprType.END;
            case 64897:
                return ExprType.ALL;
            case 64951:
                return ExprType.AND;
            case 64641:
                return ExprType.ADD;
            case 2531:
                return ExprType.OR;
            case -2137067054:
                return ExprType.IGNORE;
            case 2038860142:
                return ExprType.ROW_NUMBER;
            case 2438356:
                return ExprType.OVER;
            case 986784458:
                return ExprType.PARTITION;
            case -407597414:
                return ExprType.TO_CHAR;
            case -531820147:
                return ExprType.TO_NUMBER;
            case -407573774:
                return ExprType.TO_DATE;
            case -914382798:
                return ExprType.TO_TIMESTAMP;
            default:
                return ExprType.LETTER;
        }
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
