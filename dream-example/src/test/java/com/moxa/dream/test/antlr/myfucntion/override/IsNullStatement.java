package com.moxa.dream.test.antlr.myfucntion.override;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.CustomFunctionStatement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class IsNullStatement extends CustomFunctionStatement {
    @Override
    public String toString(ToSQL toSQL, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        switch (toSQL.getName()) {
            case "mysql":
                return toMySQL(toSQL, assist, invokerList);
            case "oracle":
                return toORACLE(toSQL, assist, invokerList);
            case "pgsql":
                return toPGSQL(toSQL, assist, invokerList);
            default:
                return toDefault(toSQL, assist, invokerList);
        }
    }

    public String toMySQL(ToSQL toSQL, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "IFNULL(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
    }

    public String toORACLE(ToSQL toSQL, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "NVL(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
    }

    public String toPGSQL(ToSQL toSQL, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "COALESCE(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
    }

    public String toDefault(ToSQL toSQL, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return "ISNULL(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
    }
}
