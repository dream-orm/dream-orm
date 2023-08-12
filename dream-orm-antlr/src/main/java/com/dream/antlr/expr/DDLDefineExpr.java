package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.DDLDefineStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

import java.util.Arrays;

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
        setExprTypes(ExprType.PRIMARY, ExprType.CONSTRAINT, ExprType.HELP);
    }

    @Override
    protected Statement exprConstraint(ExprInfo exprInfo) throws AntlrException {
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        this.constraint = symbolExpr.expr();
        setExprTypes(ExprType.PRIMARY);
        return expr();
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
        BraceExpr braceExpr = new BraceExpr(exprReader, () -> new ListColumnExpr(exprReader, () -> new SymbolExpr(exprReader), new ExprInfo(ExprType.COMMA, ",")));
        BraceStatement braceStatement = (BraceStatement) braceExpr.expr();
        ListColumnStatement statement = (ListColumnStatement) braceStatement.getStatement();
        DDLDefineStatement.DDLPrimaryKeyDefineStatement ddlPrimaryKeyDefineStatement = new DDLDefineStatement.DDLPrimaryKeyDefineStatement();
        ddlPrimaryKeyDefineStatement.setPrimaryKeys(Arrays.asList(statement.getColumnList()));
        ddlPrimaryKeyDefineStatement.setConstraint(constraint);
        this.ddlDefineStatement = ddlPrimaryKeyDefineStatement;
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
}
