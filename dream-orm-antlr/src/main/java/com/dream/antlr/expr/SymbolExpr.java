package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.MyFunctionStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

/**
 * 字段语法解析器
 */
public class SymbolExpr extends SqlExpr {
    Statement statement = null;

    public SymbolExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.STR,
                ExprType.JAVA_STR,
                ExprType.MARK,
                ExprType.SINGLE_MARK,
                ExprType.MY_FUNCTION,
                ExprType.LETTER,
                ExprType.NUMBER,
                ExprType.STAR,
                ExprType.NULL);
    }

    @Override
    public Statement nil() {
        return statement;
    }

    @Override
    protected Statement exprStar(ExprInfo exprInfo) throws AntlrException {
        push();
        statement = new SymbolStatement.LetterStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprNull(ExprInfo exprInfo) throws AntlrException {
        push();
        statement = new SymbolStatement.LetterStatement("NULL");
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprMyFunction(ExprInfo exprInfo) throws AntlrException {
        push();
        MyFunctionStatement myFunctionStatement = (MyFunctionStatement) exprInfo.getObjInfo();
        statement = new SymbolStatement.LetterStatement(myFunctionStatement.getFunctionName());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprNumber(ExprInfo exprInfo) throws AntlrException {
        push();
        statement = new SymbolStatement.NumberStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) throws AntlrException {
        ListColumnStatement listColumnStatement = (ListColumnStatement) new ListColumnExpr(exprReader, () ->
                new LetterExpr(exprReader)
                , new ExprInfo(ExprType.DOT, ".")).expr();
        Statement[] columnList = listColumnStatement.getColumnList();
        if (columnList.length > 1) {
            statement = listColumnStatement;
        } else {
            statement = columnList[0];
        }
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprStr(ExprInfo exprInfo) throws AntlrException {
        return exprLetter(exprInfo);
    }

    @Override
    protected Statement exprJavaStr(ExprInfo exprInfo) throws AntlrException {
        return exprLetter(exprInfo);
    }

    @Override
    protected Statement exprSingleMark(ExprInfo exprInfo) throws AntlrException {
        return exprLetter(exprInfo);
    }

    @Override
    protected Statement exprMark(ExprInfo exprInfo) throws AntlrException {
        push();
        statement = new SymbolStatement.MarkStatement();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    class LetterExpr extends SqlExpr {
        Statement statement = null;

        public LetterExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.LETTER, ExprType.NUMBER, ExprType.STR, ExprType.JAVA_STR, ExprType.STAR, ExprType.SINGLE_MARK, ExprType.INT);
        }

        @Override
        public Statement nil() {
            return statement;
        }

        @Override
        protected Statement exprStar(ExprInfo exprInfo) throws AntlrException {
            push();
            statement = new SymbolStatement.LetterStatement(exprInfo.getInfo());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprLetter(ExprInfo exprInfo) throws AntlrException {
            push();
            statement = new SymbolStatement.LetterStatement(exprInfo.getInfo());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprNumber(ExprInfo exprInfo) throws AntlrException {
            push();
            statement = new SymbolStatement.NumberStatement(exprInfo.getInfo());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprStr(ExprInfo exprInfo) throws AntlrException {
            push();
            statement = new SymbolStatement.StrStatement(exprInfo.getInfo());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprJavaStr(ExprInfo exprInfo) throws AntlrException {
            push();
            statement = new SymbolStatement.JavaStrStatement(exprInfo.getInfo());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprSingleMark(ExprInfo exprInfo) throws AntlrException {
            push();
            statement = new SymbolStatement.SingleMarkStatement(exprInfo.getInfo());
            setExprTypes(ExprType.NIL);
            return expr();
        }
    }
}
