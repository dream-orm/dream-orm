package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.CaseStatement;
import com.dream.antlr.smt.Statement;

/**
 * case语句语法解析器
 */
public class CaseExpr extends SqlExpr {
    private final CaseStatement caseStatement = new CaseStatement();

    public CaseExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        super(exprReader, myFunctionFactory);
        setExprTypes(ExprType.CASE);
    }

    @Override
    protected Statement exprCase(ExprInfo exprInfo) throws AntlrException {
        push();
        CaseCaseExpr caseCaseExpr = new CaseCaseExpr(exprReader, myFunctionFactory);
        caseStatement.setCaseColumn(caseCaseExpr.expr());
        setExprTypes(ExprType.WHEN);
        return expr();
    }

    @Override
    protected Statement exprWhen(ExprInfo exprInfo) throws AntlrException {
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new WhenThenExpr(exprReader, myFunctionFactory), new ExprInfo(ExprType.BLANK, " "), myFunctionFactory);
        caseStatement.setWhenThenList(listColumnExpr.expr());
        setExprTypes(ExprType.ELSE, ExprType.END);
        return expr();
    }

    @Override
    protected Statement exprElse(ExprInfo exprInfo) throws AntlrException {
        push();
        WhenThenExpr.CaseElseExpr caseElseExpr = new WhenThenExpr.CaseElseExpr(exprReader, myFunctionFactory);
        caseStatement.setElseColumn(caseElseExpr.expr());
        setExprTypes(ExprType.END);
        return expr();
    }

    @Override
    protected Statement exprEnd(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return caseStatement;
    }

    public static class CaseCaseExpr extends HelperExpr {
        Statement statement;

        public CaseCaseExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
            this(exprReader, () -> new CompareExpr(exprReader, myFunctionFactory), myFunctionFactory);
            addExprTypes(ExprType.NIL);
        }

        public CaseCaseExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
            super(exprReader, helper, myFunctionFactory);
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
            this.statement = statement;
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return this.statement;
        }
    }

    public static class WhenThenExpr extends SqlExpr {
        private final CaseStatement.WhenThenStatement whenThenStatement = new CaseStatement.WhenThenStatement();

        public WhenThenExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
            super(exprReader, myFunctionFactory);
            setExprTypes(ExprType.WHEN);
        }


        @Override
        protected Statement exprWhen(ExprInfo exprInfo) throws AntlrException {
            push();
            CaseWhenExpr caseWhenExpr = new CaseWhenExpr(exprReader, myFunctionFactory);
            Statement statement = caseWhenExpr.expr();
            whenThenStatement.setWhen(statement);
            setExprTypes(ExprType.THEN);
            return expr();
        }

        @Override
        protected Statement exprThen(ExprInfo exprInfo) throws AntlrException {
            push();
            CaseThenExpr caseThenExpr = new CaseThenExpr(exprReader, myFunctionFactory);
            whenThenStatement.setThen(caseThenExpr.expr());
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        public Statement nil() {
            return whenThenStatement;
        }

        public static class CaseWhenExpr extends HelperExpr {
            private Statement statement;

            public CaseWhenExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
                this(exprReader, () -> new CompareExpr(exprReader, myFunctionFactory), myFunctionFactory);
            }

            public CaseWhenExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
                super(exprReader, helper, myFunctionFactory);
            }

            @Override
            protected Statement exprHelp(Statement statement) throws AntlrException {
                this.statement = statement;
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            public Statement nil() {
                return statement;
            }
        }

        public static class CaseThenExpr extends HelperExpr {
            private Statement statement;

            public CaseThenExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
                this(exprReader, () -> new CompareExpr(exprReader, myFunctionFactory), myFunctionFactory);
            }

            public CaseThenExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
                super(exprReader, helper, myFunctionFactory);
            }

            @Override
            protected Statement exprHelp(Statement statement) throws AntlrException {
                this.statement = statement;
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            public Statement nil() {
                return statement;
            }
        }

        public static class CaseElseExpr extends HelperExpr {
            private Statement statement;

            public CaseElseExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
                this(exprReader, () -> new CompareExpr(exprReader, myFunctionFactory), myFunctionFactory);
            }

            public CaseElseExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
                super(exprReader, helper, myFunctionFactory);
            }

            @Override
            protected Statement exprHelp(Statement statement) throws AntlrException {
                this.statement = statement;
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            public Statement nil() {
                return statement;
            }
        }
    }
}
