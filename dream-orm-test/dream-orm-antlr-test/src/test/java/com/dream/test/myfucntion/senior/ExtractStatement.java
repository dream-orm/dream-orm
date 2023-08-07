package com.dream.test.myfucntion.senior;

import com.dream.antlr.config.Assist;
import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.HelperExpr;
import com.dream.antlr.expr.ListColumnExpr;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.MyFunctionStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

/**
 * 列如需要解析extract(year from a)这种格式，默认参数解析无法解析，必须手动实现解析器，覆写getListExpr
 */
public class ExtractStatement extends MyFunctionStatement {
    private Statement selfStatement;
    private EXTRACT_TYPE extract_type;

    public void setExtract_type(EXTRACT_TYPE extract_type) {
        this.extract_type = extract_type;
    }

    public void setSelfStatement(Statement selfStatement) {
        this.selfStatement = selfStatement;
    }

    @Override
    public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        switch (toSQL.getName()) {
            case "oracle":
                return toORACLE(toSQL, assist, invokerList);
            case "mysql":
                return toMYSQL(toSQL, assist, invokerList);
            case "pgsql":
                return toPGSQL(toSQL, assist, invokerList);
            case "mssql":
                return toMSSQL(toSQL, assist, invokerList);
            default:
                throw new RuntimeException("未支持");
        }
    }

    public String toORACLE(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        switch (extract_type) {
            case YEAR:
                return "EXTRACT(YEAR FROM " + toSQL.toStr(selfStatement, assist, invokerList) + ")";
            default:
                return "ORACLE未实现EXTRACT";
        }
    }

    public String toMSSQL(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        switch (extract_type) {
            case YEAR:
                return "YEAR(" + toSQL.toStr(selfStatement, assist, invokerList) + ")";
            default:
                throw new RuntimeException("error");
        }
    }

    public String toMYSQL(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        switch (extract_type) {
            case YEAR:
                return "YEAR(" + toSQL.toStr(selfStatement, assist, invokerList) + ")";
            default:
                throw new RuntimeException("error");
        }
    }

    public String toPGSQL(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        switch (extract_type) {
            case YEAR:
                return "YEAR(" + toSQL.toStr(selfStatement, assist, invokerList) + ")";
            default:
                throw new RuntimeException("error");
        }
    }

    @Override
    public HelperExpr.Helper getHelper(ExprReader exprReader) {
        return () -> new ListColumnExpr(exprReader, () -> new ExtractExpr(exprReader, this), new ExprInfo(ExprType.COMMA, ","));
    }

    enum EXTRACT_TYPE {
        YEAR,
        MONTH,
        DAY,
        HOUR,
        MINUTE,
        SECOND
    }
}
