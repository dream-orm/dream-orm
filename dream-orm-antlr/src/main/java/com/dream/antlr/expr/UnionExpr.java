package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.UnionStatement;

/**
 * 联合语法解析器
 */
public class UnionExpr extends HelperExpr {
    private final UnionStatement unionStatement = new UnionStatement();

    public UnionExpr(ExprReader exprReader) {
        this(exprReader, () -> new QueryExpr(exprReader));
    }

    public UnionExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.UNION);
    }

    @Override
    protected Statement exprUnion(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.LBRACE, ExprType.ALL, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprAll(ExprInfo exprInfo) throws AntlrException {
        push();
        unionStatement.setAll(true);
        setExprTypes(ExprType.LBRACE, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        unionStatement.setStatement(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return unionStatement;
    }
}
