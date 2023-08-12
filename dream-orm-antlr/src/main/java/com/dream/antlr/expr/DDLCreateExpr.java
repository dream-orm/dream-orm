package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.DDLCreateStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

/**
 * 创建语法解析器
 */
public class DDLCreateExpr extends HelperExpr {
    DDLCreateStatement ddlCreateStatement;

    public DDLCreateExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public DDLCreateExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.CREATE);
    }

    @Override
    protected Statement exprCreate(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.DATABASE, ExprType.TABLE);
        return expr();
    }

    @Override
    protected Statement exprDatabase(ExprInfo exprInfo) throws AntlrException {
        push();
        ddlCreateStatement = new DDLCreateStatement.DDLCreateDatabaseStatement();
        setExprTypes(ExprType.IF, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprTable(ExprInfo exprInfo) throws AntlrException {
        push();
        ddlCreateStatement = new DDLCreateStatement.DDLCreateTableStatement();
        setExprTypes(ExprType.IF, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprIf(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.NOT);
        return expr();
    }

    @Override
    protected Statement exprNot(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.EXISTS);
        ddlCreateStatement.setExistCreate(false);
        return expr();
    }

    @Override
    protected Statement exprExists(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement nil() {
        return ddlCreateStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        ddlCreateStatement.setStatement(statement);
        if (ddlCreateStatement instanceof DDLCreateStatement.DDLCreateDatabaseStatement) {
            setExprTypes(ExprType.NIL);
        } else if (ddlCreateStatement instanceof DDLCreateStatement.DDLCreateTableStatement) {
            setExprTypes(ExprType.LBRACE, ExprType.NIL);
        }
        return expr();
    }

    private void resetExprTypes() {
        setExprTypes(ExprType.ENGINE, ExprType.DEFAULT, ExprType.COMMENT, ExprType.NIL);
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new DDLDefineExpr(exprReader), new ExprInfo(ExprType.COMMA, ","));
        ListColumnStatement listColumnStatement = (ListColumnStatement) listColumnExpr.expr();
        ((DDLCreateStatement.DDLCreateTableStatement) ddlCreateStatement).setColumnDefineList(listColumnStatement);
        setExprTypes(ExprType.RBRACE);
        return expr();
    }

    @Override
    protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        this.resetExprTypes();
        return expr();
    }

    @Override
    protected Statement exprEngine(ExprInfo exprInfo) throws AntlrException {
        EngineExpr engineExpr = new EngineExpr(exprReader);
        ((DDLCreateStatement.DDLCreateTableStatement) ddlCreateStatement).setEngine(engineExpr.expr());
        this.resetExprTypes();
        return expr();
    }

    @Override
    protected Statement exprDefault(ExprInfo exprInfo) throws AntlrException {
        DefaultCharsetExpr charsetExpr = new DefaultCharsetExpr(exprReader);
        ((DDLCreateStatement.DDLCreateTableStatement) ddlCreateStatement).setDefaultCharset(charsetExpr.expr());
        this.resetExprTypes();
        return expr();
    }

    @Override
    protected Statement exprComment(ExprInfo exprInfo) throws AntlrException {
        CommentExpr commentExpr = new CommentExpr(exprReader);
        ((DDLCreateStatement.DDLCreateTableStatement) ddlCreateStatement).setComment(commentExpr.expr());
        this.resetExprTypes();
        return expr();
    }

    class EngineExpr extends HelperExpr {
        private Statement statement;

        public EngineExpr(ExprReader exprReader) {
            this(exprReader, () -> new SymbolExpr(exprReader));
        }

        public EngineExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
            setExprTypes(ExprType.ENGINE);
        }

        @Override
        protected Statement exprEngine(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.EQ);
            return expr();
        }

        @Override
        protected Statement exprEq(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.HELP);
            return expr();
        }

        @Override
        protected Statement nil() {
            return statement;
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
            this.statement = statement;
            setExprTypes(ExprType.NIL);
            return expr();
        }
    }

    class DefaultCharsetExpr extends HelperExpr {
        private Statement statement;

        public DefaultCharsetExpr(ExprReader exprReader) {
            this(exprReader, () -> new SymbolExpr(exprReader));
        }

        public DefaultCharsetExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
            setExprTypes(ExprType.DEFAULT);
        }

        @Override
        protected Statement exprDefault(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.CHARSET);
            return expr();
        }

        @Override
        protected Statement exprCharset(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.EQ);
            return expr();
        }

        @Override
        protected Statement exprEq(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.HELP);
            return expr();
        }

        @Override
        protected Statement nil() {
            return statement;
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
            this.statement = statement;
            setExprTypes(ExprType.NIL);
            return expr();
        }
    }

    class CommentExpr extends HelperExpr {
        private Statement statement;

        public CommentExpr(ExprReader exprReader) {
            this(exprReader, () -> new SymbolExpr(exprReader));
        }

        public CommentExpr(ExprReader exprReader, Helper helper) {
            super(exprReader, helper);
            setExprTypes(ExprType.COMMENT);
        }

        @Override
        protected Statement exprComment(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.EQ);
            return expr();
        }

        @Override
        protected Statement exprEq(ExprInfo exprInfo) throws AntlrException {
            push();
            setExprTypes(ExprType.HELP);
            return expr();
        }

        @Override
        protected Statement nil() {
            return statement;
        }

        @Override
        protected Statement exprHelp(Statement statement) throws AntlrException {
            this.statement = statement;
            setExprTypes(ExprType.NIL);
            return expr();
        }
    }
}
