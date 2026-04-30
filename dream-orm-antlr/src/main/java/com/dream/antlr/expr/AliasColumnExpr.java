package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.AliasStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

/**
 * 别名语法解析器
 */
public class AliasColumnExpr extends HelperExpr {
    AliasStatement aliasStatement = new AliasStatement();
    private static final ExprType[] ALIAS = {
            ExprType.LETTER, ExprType.SINGLE_MARK, ExprType.STR, ExprType.JAVA_STR,
            ExprType.ASCII,
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
            ExprType.CURRENT_DATE,
            ExprType.CURTIME,
            ExprType.CURRENT_TIME,
            ExprType.DATEDIFF,
            ExprType.UNIX_TIMESTAMP,
            ExprType.FROM_UNIXTIME,
            ExprType.DATE,
            ExprType.TIMESTAMP,
            ExprType.DATE_ADD,
            ExprType.DATE_SUB,
            ExprType.DATE_FORMAT,
            ExprType.EXTRACT,
            ExprType.DAY,
            ExprType.DAYOFMONTH,
            ExprType.DAYOFWEEK,
            ExprType.DAYOFYEAR,
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
            ExprType.CAST,
            ExprType.CONVERT,
            ExprType.COALESCE,
            ExprType.CONCAT,
            ExprType.GROUP_CONCAT,
            ExprType.ISNULL,
            ExprType.IFNULL,
            ExprType.IF,
            ExprType.NULLIF,
            ExprType.SPACE,
            ExprType.MY_FUNCTION,
            ExprType.TO_CHAR,
            ExprType.TO_NUMBER,
            ExprType.TO_DATE,
            ExprType.TO_TIMESTAMP,
    };

    public AliasColumnExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        this(exprReader, () -> new CompareExpr(exprReader, myFunctionFactory), myFunctionFactory);
    }

    public AliasColumnExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
        super(exprReader, helper, myFunctionFactory);
        setExprTypes(ExprType.HELP, ExprType.STAR);
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        aliasStatement.setColumn(statement);
        setExprTypes(ALIAS).addExprTypes(ExprType.AS, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSelf(ExprInfo exprInfo) throws AntlrException {
        ColumnExpr symbolExpr = new ColumnExpr(exprReader, myFunctionFactory);
        aliasStatement.setAlias(symbolExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStar(ExprInfo exprInfo) throws AntlrException {
        push();
        aliasStatement.setColumn(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprAs(ExprInfo exprInfo) throws AntlrException {
        push();
        aliasStatement.setShowAlias(true);
        setExprTypes(ALIAS);
        return expr();
    }

    @Override
    public Statement nil() {
        if (aliasStatement.getAlias() == null) {
            return aliasStatement.getColumn();
        } else {
            return aliasStatement;
        }
    }
}
