package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.AliasStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

/**
 * 别名语法解析器
 */
public class AliasColumnExpr extends HelperExpr {
    AliasStatement aliasStatement = new AliasStatement();

    public AliasColumnExpr(ExprReader exprReader) {
        this(exprReader, () -> new CompareExpr(exprReader));
    }

    public AliasColumnExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.HELP, ExprType.STAR);
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        aliasStatement.setColumn(statement);
        setExprTypes(ExprType.LETTER, ExprType.SINGLE_MARK, ExprType.STR, ExprType.JAVA_STR, ExprType.AS, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) throws AntlrException {
        return exprAlias(exprInfo);
    }

    @Override
    protected Statement exprSingleMark(ExprInfo exprInfo) throws AntlrException {
        return exprAlias(exprInfo);
    }

    @Override
    protected Statement exprStr(ExprInfo exprInfo) throws AntlrException {
        return exprAlias(exprInfo);
    }

    @Override
    protected Statement exprJavaStr(ExprInfo exprInfo) throws AntlrException {
        return exprAlias(exprInfo);
    }

    protected Statement exprAlias(ExprInfo exprInfo) throws AntlrException {
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
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
        setExprTypes(ExprType.LETTER, ExprType.SINGLE_MARK, ExprType.STR, ExprType.JAVA_STR);
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
