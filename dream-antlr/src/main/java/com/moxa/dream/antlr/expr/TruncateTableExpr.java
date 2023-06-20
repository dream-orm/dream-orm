package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.TruncateTableStatement;

public class TruncateTableExpr extends HelperExpr {

    private final TruncateTableStatement truncateTableStatement = new TruncateTableStatement();

    public TruncateTableExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public TruncateTableExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.TRUNCATE);
    }

    @Override
    protected Statement exprTruncate(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.TABLE);
        return expr();
    }

    @Override
    protected Statement exprTable(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }


    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        truncateTableStatement.setTable(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return truncateTableStatement;
    }
}
