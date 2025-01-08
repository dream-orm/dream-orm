package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.smt.WithStatement;

public class WithExpr extends SqlExpr {
    private final WithStatement withStatement = new WithStatement();

    public WithExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.WITH);
    }

    @Override
    protected Statement exprWith(ExprInfo exprInfo) throws AntlrException {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new WithAliasExpr(exprReader), new ExprInfo(ExprType.COMMA, ","));
        withStatement.setAliasStatement(listColumnExpr.expr());
        DMLExpr dmlExpr = new DMLExpr(exprReader);
        withStatement.setStatement(dmlExpr.expr());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return withStatement;
    }
}

class WithAliasExpr extends HelperExpr {
    private final WithStatement.WithAliasStatement withAliasStatement = new WithStatement.WithAliasStatement();

    public WithAliasExpr(ExprReader exprReader) {
        this(exprReader, () -> new BraceExpr(exprReader));
    }

    public WithAliasExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.LETTER, ExprType.SINGLE_MARK);
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) throws AntlrException {
        push();
        withAliasStatement.setAliasStatement(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.AS);
        return expr();
    }

    @Override
    protected Statement exprSingleMark(ExprInfo exprInfo) throws AntlrException {
        push();
        withAliasStatement.setAliasStatement(new SymbolStatement.SingleMarkStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.AS);
        return expr();
    }

    @Override
    protected Statement exprAs(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement nil() {
        return withAliasStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        withAliasStatement.setStatement(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }
}
