package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.DDLAlterStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

/**
 * 修改表修改字段语法解析器
 */
public class DDLAlterModifyExpr extends HelperExpr {

    private DDLAlterStatement.DDLAlterModifyStatement ddlAlterModifyStatement;

    public DDLAlterModifyExpr(ExprReader exprReader, DDLAlterStatement.DDLAlterModifyStatement ddlAlterModifyStatement) {
        this(exprReader, () -> new SymbolExpr(exprReader), ddlAlterModifyStatement);
    }

    public DDLAlterModifyExpr(ExprReader exprReader, Helper helper, DDLAlterStatement.DDLAlterModifyStatement ddlAlterModifyStatement) {
        super(exprReader, helper);
        this.ddlAlterModifyStatement = ddlAlterModifyStatement;
        setExprTypes(ExprType.MODIFY);
    }

    @Override
    protected Statement exprModify(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.COLUMN, ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprColumn(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement nil() {
        return ddlAlterModifyStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        if (ddlAlterModifyStatement.getColumn() == null) {
            ddlAlterModifyStatement.setColumn(statement);
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
                    ExprType.YEAR
            );
        } else {
            ddlAlterModifyStatement.setDefaultValue(statement);
            setExprTypes(ExprType.NOT, ExprType.NIL);
        }
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
        ddlAlterModifyStatement.setColumnType(exprInfo.getExprType());
        BraceExpr braceExpr = new BraceExpr(exprReader, () -> new ListColumnExpr(exprReader, () -> new ColumnExpr(exprReader), new ExprInfo(ExprType.COMMA, ",")));
        braceExpr.addExprTypes(ExprType.NIL);
        BraceStatement braceStatement = (BraceStatement) braceExpr.expr();
        ddlAlterModifyStatement.setColumnTypeParamList((ListColumnStatement) braceStatement.getStatement());
        setExprTypes(ExprType.DEFAULT, ExprType.NOT, ExprType.NULL);
        return expr();
    }

    @Override
    protected Statement exprDefault(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprNot(ExprInfo exprInfo) throws AntlrException {
        push();
        ddlAlterModifyStatement.setNullFlag(false);
        setExprTypes(ExprType.NULL);
        return expr();
    }

    @Override
    protected Statement exprNull(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.DEFAULT, ExprType.NIL);
        return expr();
    }
}
