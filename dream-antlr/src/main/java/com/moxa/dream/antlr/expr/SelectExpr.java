package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.PreSelectStatement;
import com.moxa.dream.antlr.smt.SelectStatement;
import com.moxa.dream.antlr.smt.Statement;

public class SelectExpr extends SqlExpr {
    private final SelectStatement selectStatement = new SelectStatement();

    public SelectExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.SELECT);
    }

    @Override
    protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {

        PreSelectExpr preSelectExpr = new PreSelectExpr(exprReader);
        preSelectExpr.setExprTypes(ExprType.SELECT);
        PreSelectStatement preSelect = (PreSelectStatement) preSelectExpr.expr();
        selectStatement.setPreSelect(preSelect);

        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader,
                () -> new AliasColumnExpr(exprReader),
                new ExprInfo(ExprType.COMMA, ","));
        ListColumnStatement listColumnStatement = (ListColumnStatement) listColumnExpr.expr();
        selectStatement.setSelectList(listColumnStatement);
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return selectStatement;
    }

    class PreSelectExpr extends SqlExpr {
        private final PreSelectStatement preSelectStatement = new PreSelectStatement();

        public PreSelectExpr(ExprReader exprReader) {
            super(exprReader);
            setExprTypes(ExprType.SELECT);
        }

        @Override
        protected Statement exprSelect(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.DISTINCT, ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement exprDistinct(ExprInfo exprInfo) throws AntlrException {
            preSelectStatement.setDistinct(true);
            push();
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return preSelectStatement;
        }
    }
}
