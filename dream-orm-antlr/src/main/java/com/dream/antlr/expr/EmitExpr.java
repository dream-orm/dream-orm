package com.dream.antlr.expr;

import com.dream.antlr.config.Constant;
import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.EmitStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

/**
 * 冒号语法解析器
 */
public class EmitExpr extends HelperExpr {

    private EmitStatement emitStatement;

    public EmitExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public EmitExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.COLON, ExprType.DOLLAR, ExprType.SHARP);
    }

    @Override
    protected Statement exprColon(ExprInfo exprInfo) throws AntlrException {
        push();
        emitStatement = new EmitStatement.ColonStatement();
        setExprTypes(Constant.KEYWORD).addExprTypes(Constant.FUNCTION).addExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprDollar(ExprInfo exprInfo) throws AntlrException {
        push();
        emitStatement = new EmitStatement.DollarStatement();
        setExprTypes(Constant.KEYWORD).addExprTypes(Constant.FUNCTION).addExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprSharp(ExprInfo exprInfo) throws AntlrException {
        push();
        emitStatement = new EmitStatement.SharpStatement();
        setExprTypes(Constant.KEYWORD).addExprTypes(Constant.FUNCTION).addExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprKeyWord(ExprInfo exprInfo) throws AntlrException {
        push();
        emitStatement.setStatement(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFunction(ExprInfo exprInfo) throws AntlrException {
        push();
        emitStatement.setStatement(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        emitStatement.setStatement(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return emitStatement;
    }

}
