package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.DropTableStatement;
import com.moxa.dream.antlr.smt.Statement;

public class DropTableExpr extends HelperExpr {

    private final DropTableStatement dropTableStatement = new DropTableStatement();

    public DropTableExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public DropTableExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.DROP);
    }

    @Override
    protected Statement exprDrop(ExprInfo exprInfo) throws AntlrException {
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
        dropTableStatement.setTable(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return dropTableStatement;
    }
}
