package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.DDLDefineStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

/**
 * 字段定义语法解析器
 */
public class DDLDefineExpr extends HelperExpr {
    private DDLDefineStatement ddlDefineStatement;
    private Statement constraint;

    public DDLDefineExpr(ExprReader exprReader) {
        this(exprReader, () -> new DDLColumnDefineExpr(exprReader));
    }

    public DDLDefineExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.CONSTRAINT, ExprType.PRIMARY, ExprType.FOREIGN, ExprType.HELP);
    }

    @Override
    protected Statement exprConstraint(ExprInfo exprInfo) throws AntlrException {
        push();
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        this.constraint = symbolExpr.expr();
        setExprTypes(ExprType.PRIMARY, ExprType.FOREIGN);
        return expr();
    }

    @Override
    protected Statement exprPrimary(ExprInfo exprInfo) throws AntlrException {
        DDLDefineStatement.DDLPrimaryKeyDefineStatement ddlPrimaryKeyDefineStatement = (DDLDefineStatement.DDLPrimaryKeyDefineStatement) new DDLPrimaryKeyDefineExpr(exprReader).expr();
        ddlPrimaryKeyDefineStatement.setConstraint(constraint);
        this.ddlDefineStatement = ddlPrimaryKeyDefineStatement;
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprForeign(ExprInfo exprInfo) throws AntlrException {
        DDLDefineStatement.DDLForeignKeyDefineStatement ddlForeignKeyDefineStatement = (DDLDefineStatement.DDLForeignKeyDefineStatement) new DDLForeignKeyDefineExpr(exprReader).expr();
        ddlForeignKeyDefineStatement.setConstraint(constraint);
        this.ddlDefineStatement = ddlForeignKeyDefineStatement;
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        this.ddlDefineStatement = (DDLDefineStatement) statement;
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return ddlDefineStatement;
    }

    class DDLPrimaryKeyDefineExpr extends HelperExpr {
        private DDLDefineStatement.DDLPrimaryKeyDefineStatement ddlPrimaryKeyDefineStatement = new DDLDefineStatement.DDLPrimaryKeyDefineStatement();

        public DDLPrimaryKeyDefineExpr(ExprReader exprReader) {
            this(exprReader, () -> new ListColumnExpr(exprReader, () -> new SymbolExpr(exprReader), new ExprInfo(ExprType.COMMA, ",")));
        }

        public DDLPrimaryKeyDefineExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
            setExprTypes(ExprType.PRIMARY);
        }

        @Override
        protected Statement exprPrimary(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.KEY);
            return expr();
        }

        @Override
        protected Statement exprKey(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.LBRACE);
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
            ddlPrimaryKeyDefineStatement.setPrimaryKeys((ListColumnStatement) statement);
            setExprTypes(ExprType.RBRACE);
            return expr();
        }

        @Override
        protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement nil() {
            return ddlPrimaryKeyDefineStatement;
        }
    }

    class DDLForeignKeyDefineExpr extends HelperExpr {
        private DDLDefineStatement.DDLForeignKeyDefineStatement ddlForeignKeyDefineStatement = new DDLDefineStatement.DDLForeignKeyDefineStatement();

        public DDLForeignKeyDefineExpr(ExprReader exprReader) {
            this(exprReader, () -> new SymbolExpr(exprReader));
        }

        public DDLForeignKeyDefineExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
            setExprTypes(ExprType.FOREIGN);
        }

        @Override
        protected Statement exprForeign(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.KEY);
            return expr();
        }

        @Override
        protected Statement exprKey(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.LBRACE);
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
            ddlForeignKeyDefineStatement.setForeignKey(statement);
            setExprTypes(ExprType.RBRACE);
            return expr();
        }

        @Override
        protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.REFERENCES);
            return expr();
        }

        @Override
        protected Statement exprReferences(ExprInfo exprInfo) throws AntlrException {
            ddlForeignKeyDefineStatement = (DDLDefineStatement.DDLForeignKeyDefineStatement) new DDLReferencesDefineExpr(exprReader, ddlForeignKeyDefineStatement).expr();
            setExprTypes(ExprType.NIL);
            return expr();
        }

        @Override
        protected Statement nil() {
            return ddlForeignKeyDefineStatement;
        }

        class DDLReferencesDefineExpr extends HelperExpr {
            private DDLDefineStatement.DDLForeignKeyDefineStatement ddlForeignKeyDefineStatement;

            public DDLReferencesDefineExpr(ExprReader exprReader, DDLDefineStatement.DDLForeignKeyDefineStatement ddlForeignKeyDefineStatement) {
                this(exprReader, () -> new SymbolExpr(exprReader), ddlForeignKeyDefineStatement);
            }

            public DDLReferencesDefineExpr(ExprReader exprReader, Helper helper, DDLDefineStatement.DDLForeignKeyDefineStatement ddlForeignKeyDefineStatement) {
                super(exprReader, helper);
                setExprTypes(ExprType.REFERENCES);
                this.ddlForeignKeyDefineStatement = ddlForeignKeyDefineStatement;
            }

            @Override
            protected Statement exprHelp(Statement statement) throws AntlrException {
                if (ddlForeignKeyDefineStatement.getForeignTable() == null) {
                    ddlForeignKeyDefineStatement.setForeignTable(statement);
                    setExprTypes(ExprType.LBRACE);
                } else {
                    ddlForeignKeyDefineStatement.setForeignColumn(statement);
                    setExprTypes(ExprType.RBRACE);
                }
                return expr();
            }

            @Override
            protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.HELP);
                return expr();
            }

            @Override
            protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.NIL);
                return expr();
            }

            @Override
            protected Statement exprReferences(ExprInfo exprInfo) throws AntlrException {
                push();
                setExprTypes(ExprType.HELP);
                return expr();
            }

            @Override
            protected Statement nil() {
                return ddlForeignKeyDefineStatement;
            }
        }
    }
}
