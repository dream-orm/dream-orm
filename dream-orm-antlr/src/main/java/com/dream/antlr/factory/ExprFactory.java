package com.dream.antlr.factory;

import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.SqlExpr;
import com.dream.antlr.smt.Statement;

public interface ExprFactory {
    Statement apply(SqlExpr sqlExpr) throws AntlrException;
}
