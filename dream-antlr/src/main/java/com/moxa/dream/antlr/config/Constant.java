package com.moxa.dream.antlr.config;


public final class Constant {
    public static final ExprType[] FUNCTION = {
            ExprType.ASCII,
            ExprType.LEN,
            ExprType.LENGTH,
            ExprType.CHAR_LENGTH,
            ExprType.CONCAT_WS,
            ExprType.FIND_IN_SET,
            ExprType.INSTR,
            ExprType.LOCATE,
            ExprType.LCASE,
            ExprType.LOWER,
            ExprType.CHARACTER_LENGTH,
            ExprType.LTRIM,
            ExprType.REPEAT,
            ExprType.REVERSE,
            ExprType.REPLACE,
            ExprType.RTRIM,
            ExprType.STRCMP,
            ExprType.SUBSTR,
            ExprType.SUBSTRING,
            ExprType.TRIM,
            ExprType.UCASE,
            ExprType.UPPER,
            ExprType.LPAD,
            ExprType.RPAD,
            ExprType.ABS,
            ExprType.AVG,
            ExprType.ACOS,
            ExprType.ASIN,
            ExprType.SIN,
            ExprType.ATAN,
            ExprType.CEIL,
            ExprType.CEILING,
            ExprType.COS,
            ExprType.COT,
            ExprType.COUNT,
            ExprType.EXP,
            ExprType.FLOOR,
            ExprType.LN,
            ExprType.LOG,
            ExprType.LOG10,
            ExprType.LOG2,
            ExprType.MAX,
            ExprType.MIN,
            ExprType.MOD,
            ExprType.PI,
            ExprType.POW,
            ExprType.POWER,
            ExprType.RAND,
            ExprType.ROUND,
            ExprType.SIGN,
            ExprType.SQRT,
            ExprType.SUM,
            ExprType.TAN,
            ExprType.TRUNCATE,
            ExprType.CURDATE,
            ExprType.DATEDIFF,
            ExprType.UNIX_TIMESTAMP,
            ExprType.FROM_UNIXTIME,
            ExprType.DATE,
            ExprType.DATE_ADD,
            ExprType.DATE_SUB,
            ExprType.DATE_FORMAT,
            ExprType.DAY,
            ExprType.DAYOFMONTH,
            ExprType.DAYOFWEEK,
            ExprType.DAYOFYEAR,
            ExprType.TO_CHAR,
            ExprType.TO_NUMBER,
            ExprType.TO_DATE,
            ExprType.HOUR,
            ExprType.LAST_DAY,
            ExprType.MINUTE,
            ExprType.MONTH,
            ExprType.NOW,
            ExprType.SYSDATE,
            ExprType.QUARTER,
            ExprType.SECOND,
            ExprType.TIME,
            ExprType.STR_TO_DATE,
            ExprType.WEEK,
            ExprType.WEEKOFYEAR,
            ExprType.YEAR,
            ExprType.ROW_NUMBER,
            ExprType.OVER,
            ExprType.CAST,
            ExprType.CONVERT,
            ExprType.COALESCE,
            ExprType.CONCAT,
            ExprType.GROUP_CONCAT,
            ExprType.ISNULL,
            ExprType.LEFT,
            ExprType.RIGHT,
            ExprType.IFNULL,
            ExprType.IF,
            ExprType.NULLIF,
            ExprType.SPACE,
            ExprType.MY_FUNCTION
    };

    public static final ExprType[] SYMBOL = {
            ExprType.INT,
            ExprType.LONG,
            ExprType.FLOAT,
            ExprType.DOUBLE,
            ExprType.BOOLEAN,
            ExprType.STR,
            ExprType.JAVA_STR,
            ExprType.LETTER,
            ExprType.SKIP,
            ExprType.MARK
    };
    public static final ExprType[] KEYWORD = {
            ExprType.UNION,
            ExprType.NOT,
            ExprType.EXISTS,
            ExprType.OFFSET,
            ExprType.INTERVAL,
            ExprType.ALL,
            ExprType.BETWEEN,
            ExprType.SET,
            ExprType.UPDATE,
            ExprType.DELETE,
            ExprType.VALUES,
            ExprType.INTO,
            ExprType.INSERT,
            ExprType.ASC,
            ExprType.DESC,
            ExprType.HAVING,
            ExprType.PARTITION,
            ExprType.LIMIT,
            ExprType.GROUP,
            ExprType.BY,
            ExprType.INNER,
            ExprType.ON,
            ExprType.LIKE,
            ExprType.ACC,
            ExprType.END,
            ExprType.ELSE,
            ExprType.THEN,
            ExprType.JOIN,
            ExprType.OUTER,
            ExprType.CROSS,
            ExprType.OR,
            ExprType.AND,
            ExprType.WHERE,
            ExprType.CASE,
            ExprType.DISTINCT,
            ExprType.FROM,
            ExprType.SELECT,
            ExprType.SEPARATOR,
            ExprType.AS
    };

}