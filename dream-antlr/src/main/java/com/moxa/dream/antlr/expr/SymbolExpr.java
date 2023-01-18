package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.Constant;
import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class SymbolExpr extends SqlExpr {
    Statement statement = null;

    public SymbolExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(Constant.SYMBOL).addExprTypes(ExprType.STAR);
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
    protected Statement exprInt(ExprInfo exprInfo) throws AntlrException {
        push();
        statement = new SymbolStatement.IntStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLong(ExprInfo exprInfo) throws AntlrException {
        push();
        statement = new SymbolStatement.LongStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFloat(ExprInfo exprInfo) throws AntlrException {
        push();
        statement = new SymbolStatement.FloatStatement(exprInfo.getInfo());
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprDouble(ExprInfo exprInfo) throws AntlrException {
        push();
        statement = new SymbolStatement.DoubleStatement(exprInfo.getInfo());
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
            setExprTypes(ExprType.LETTER, ExprType.STR, ExprType.JAVA_STR, ExprType.STAR, ExprType.SINGLE_MARK, ExprType.INT);
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

        @Override
        protected Statement exprInt(ExprInfo exprInfo) throws AntlrException {
            push();
            statement = new SymbolStatement.IntStatement(exprInfo.getInfo());
            setExprTypes(ExprType.NIL);
            return expr();
        }
    }
}
