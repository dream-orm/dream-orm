package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.ConditionStatement;
import com.moxa.dream.antlr.smt.OperStatement;
import com.moxa.dream.antlr.smt.Statement;

public class CompareExpr extends TreeExpr {
    public static final ExprType[] COMPARE = {ExprType.LT, ExprType.GT, ExprType.LEQ, ExprType.NEQ, ExprType.EQ, ExprType.GEQ, ExprType.IS, ExprType.IN, ExprType.NOT, ExprType.EXISTS, ExprType.LIKE, ExprType.BETWEEN};
    public static final ExprType[] CONDITION = {ExprType.AND, ExprType.OR};

    public CompareExpr(ExprReader exprReader) {
        this(exprReader, () -> new OperExpr(exprReader));
    }

    public CompareExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.NOT, ExprType.EXISTS, ExprType.HELP, ExprType.LBRACE);
    }

    @Override
    public Statement nil() {
        if (top.getOper() != null) {
            return top;
        } else {
            return top.getLeft();
        }

    }

    @Override
    protected Statement exprAnd(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.ANDStatement());
        this.setExprTypes(ExprType.NOT, ExprType.EXISTS, ExprType.HELP, ExprType.LBRACE);
        return expr();
    }

    @Override
    protected Statement exprOr(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.ORStatement());
        this.setExprTypes(ExprType.NOT, ExprType.EXISTS, ExprType.HELP, ExprType.LBRACE);
        return expr();
    }

    @Override
    protected Statement exprLt(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.LTStatement());
        setExprTypes(ExprType.LBRACE, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprLeq(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.LEQStatement());
        setExprTypes(ExprType.LBRACE, ExprType.HELP);
        return expr();

    }

    @Override
    protected Statement exprGt(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.GTStatement());
        setExprTypes(ExprType.LBRACE, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprGeq(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.GEQStatement());
        setExprTypes(ExprType.LBRACE, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprEq(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.EQStatement());
        setExprTypes(ExprType.LBRACE, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprNeq(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.NEQStatement());
        setExprTypes(ExprType.LBRACE, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprLike(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.LIKEStatement());
        setExprTypes(ExprType.LBRACE, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprBetween(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.BETWEENStatement());
        BetweenAndExpr betweenAndExpr = new BetweenAndExpr(exprReader);
        exprTree(betweenAndExpr.expr());
        setExprTypes(ExprType.AND, ExprType.OR, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprIs(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.ISStatement());
        setExprTypes(ExprType.NOT, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprIn(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.INStatement());
        setExprTypes(ExprType.LBRACE);
        return expr();
    }

    @Override
    protected Statement exprNot(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.NOTStatement());
        setExprTypes(ExprType.NOT, ExprType.IN, ExprType.LIKE, ExprType.IS, ExprType.EXISTS, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprExists(ExprInfo exprInfo) throws AntlrException {
        push();
        exprTree(new OperStatement.EXISTSStatement());
        setExprTypes(ExprType.LBRACE);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        BraceExpr braceExpr = new BraceExpr(exprReader);
        BraceStatement braceStatement = (BraceStatement) braceExpr.expr();
        exprTree(braceStatement);
        setExprTypes(COMPARE).addExprTypes(CONDITION).addExprTypes(OperExpr.OPER).addExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprSelf(ExprInfo exprInfo) throws AntlrException {
        OperExpr operExpr = new OperExpr(exprReader, this);
        operExpr.setExprTypes(OperExpr.OPER);
        operExpr.expr();
        setExprTypes(COMPARE).addExprTypes(CONDITION).addExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        exprTree(statement);
        setExprTypes(COMPARE).addExprTypes(CONDITION).addExprTypes(ExprType.NIL);
        return expr();
    }

    public static class BetweenAndExpr extends HelperExpr {
        private final ConditionStatement bet = new ConditionStatement();

        public BetweenAndExpr(ExprReader exprReader) {
            this(exprReader, () -> new OperExpr(exprReader));
        }

        public BetweenAndExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
            if (bet.getLeft() == null) {
                bet.setLeft(statement);
                setExprTypes(ExprType.AND);
            } else {
                bet.setRight(statement);
                setExprTypes(ExprType.NIL);
            }
            return expr();
        }

        @Override
        protected Statement exprAnd(ExprInfo exprInfo) throws AntlrException {
            push();
            bet.setOper(new OperStatement.ANDStatement());
            setExprTypes(ExprType.HELP);
            return expr();
        }

        @Override
        public Statement nil() {
            return bet;
        }
    }
}
