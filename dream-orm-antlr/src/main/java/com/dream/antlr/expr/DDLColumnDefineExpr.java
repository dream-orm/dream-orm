package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.DDLDefineStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

/**
 * 字段定义语法解析器
 */
public class DDLColumnDefineExpr extends HelperExpr {
    private DDLDefineStatement.DDLColumnDefineStatement ddlColumnDefineStatement = new DDLDefineStatement.DDLColumnDefineStatement();

    public DDLColumnDefineExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader));
    }

    public DDLColumnDefineExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.HELP);
    }

    private void resetTypes() {
        setExprTypes(ExprType.TINYINT,
                ExprType.SMALLINT,
                ExprType.MEDIUMINT,
                ExprType.INT,
                ExprType.INTEGER,
                ExprType.BIGINT,
                ExprType.FLOAT,
                ExprType.DOUBLE,
                ExprType.DECIMAL,
                ExprType.VARCHAR,
                ExprType.CHAR,
                ExprType.TEXT,
                ExprType.BLOB,
                ExprType.DATE,
                ExprType.DATETIME,
                ExprType.TIMESTAMP,
                ExprType.TIME,
                ExprType.YEAR,
                ExprType.COMMENT,
                ExprType.NOT,
                ExprType.DEFAULT,
                ExprType.AUTO_INCREMENT,
                ExprType.PRIMARY,
                ExprType.NIL
        );
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        ddlColumnDefineStatement.setColumn(statement);
        this.resetTypes();
        return expr();
    }

    @Override
    protected Statement exprTinyInt(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprSmallInt(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprMediumInt(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprInt(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprInteger(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprBigInt(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprText(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprBlob(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprVarChar(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprChar(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprDateTime(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprTimeStamp(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprTime(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprYear(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    @Override
    protected Statement exprDate(ExprInfo exprInfo) throws AntlrException {
        return exprDataType(exprInfo);
    }

    private Statement exprDataType(ExprInfo exprInfo) throws AntlrException {
        push();
        ddlColumnDefineStatement.setColumnType(exprInfo.getExprType());
        BraceExpr braceExpr = new BraceExpr(exprReader, () -> new ListColumnExpr(exprReader, () -> new ColumnExpr(exprReader), new ExprInfo(ExprType.COMMA, ",")));
        braceExpr.addExprTypes(ExprType.NIL);
        BraceStatement braceStatement = (BraceStatement) braceExpr.expr();
        ddlColumnDefineStatement.setColumnTypeParamList((ListColumnStatement) braceStatement.getStatement());
        this.resetTypes();
        return expr();
    }

    @Override
    protected Statement exprNot(ExprInfo exprInfo) throws AntlrException {
        push();
        ddlColumnDefineStatement.setNullFlag(false);
        setExprTypes(ExprType.NULL);
        return expr();
    }

    @Override
    protected Statement exprNull(ExprInfo exprInfo) throws AntlrException {
        push();
        this.resetTypes();
        return expr();
    }

    @Override
    protected Statement exprAutoIncrement(ExprInfo exprInfo) throws AntlrException {
        push();
        ddlColumnDefineStatement.setAutoIncrement(true);
        this.resetTypes();
        return expr();
    }

    @Override
    protected Statement exprPrimary(ExprInfo exprInfo) throws AntlrException {
        push();
        ddlColumnDefineStatement.setPrimaryKey(true);
        setExprTypes(ExprType.KEY);
        return expr();
    }

    @Override
    protected Statement exprKey(ExprInfo exprInfo) throws AntlrException {
        push();
        this.resetTypes();
        return expr();
    }

    @Override
    protected Statement exprComment(ExprInfo exprInfo) throws AntlrException {
        push();
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        ddlColumnDefineStatement.setComment(symbolExpr.expr());
        this.resetTypes();
        return expr();
    }

    @Override
    protected Statement exprDefault(ExprInfo exprInfo) throws AntlrException {
        push();
        SymbolExpr symbolExpr = new SymbolExpr(exprReader);
        ddlColumnDefineStatement.setDefaultValue(symbolExpr.expr());
        this.resetTypes();
        return expr();
    }

    @Override
    protected Statement nil() {
        return ddlColumnDefineStatement;
    }
}
