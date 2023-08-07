package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.DropTableStatement;
import com.dream.antlr.smt.Statement;

/**
 * 删除表语法解析器
 */
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
