package com.moxa.dream.test.myfucntion.senior;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.expr.ColumnExpr;
import com.moxa.dream.antlr.expr.HelperExpr;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;


public class ExtractExpr extends HelperExpr {
    ExtractStatement extractStatement;

    public ExtractExpr(ExprReader exprReader, ExtractStatement extractStatement) {
        this(exprReader, () -> new ColumnExpr(exprReader), extractStatement);
    }

    public ExtractExpr(ExprReader exprReader, Helper helper, ExtractStatement extractStatement) {
        super(exprReader, helper);
        //设置接受的类型，重写接受函数
        setExprTypes(ExprType.YEAR, ExprType.MONTH, ExprType.DAY, ExprType.HOUR, ExprType.MINUTE, ExprType.SECOND);
        this.extractStatement = extractStatement;

    }

    @Override
    protected Statement exprYear(ExprInfo exprInfo) throws AntlrException {
        push();
        extractStatement.setExtract_type(ExtractStatement.EXTRACT_TYPE.YEAR);
        setExprTypes(ExprType.FROM);
        return expr();
    }

    @Override
    protected Statement exprMonth(ExprInfo exprInfo) throws AntlrException {
        push();
        extractStatement.setExtract_type(ExtractStatement.EXTRACT_TYPE.MONTH);
        setExprTypes(ExprType.FROM);
        return expr();
    }

    @Override
    protected Statement exprDay(ExprInfo exprInfo) throws AntlrException {
        push();
        extractStatement.setExtract_type(ExtractStatement.EXTRACT_TYPE.DAY);
        setExprTypes(ExprType.FROM);
        return expr();
    }

    @Override
    protected Statement exprHour(ExprInfo exprInfo) throws AntlrException {
        push();
        extractStatement.setExtract_type(ExtractStatement.EXTRACT_TYPE.HOUR);
        setExprTypes(ExprType.FROM);
        return expr();
    }

    @Override
    protected Statement exprMinute(ExprInfo exprInfo) throws AntlrException {
        push();
        extractStatement.setExtract_type(ExtractStatement.EXTRACT_TYPE.MINUTE);
        setExprTypes(ExprType.FROM);
        return expr();
    }

    @Override
    protected Statement exprSecond(ExprInfo exprInfo) throws AntlrException {
        push();
        extractStatement.setExtract_type(ExtractStatement.EXTRACT_TYPE.SECOND);
        setExprTypes(ExprType.FROM);
        return expr();
    }

    @Override
    protected Statement exprFrom(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    //规约
    @Override
    protected Statement nil() {
//        return extractStatement;
        return null;
    }

    @Override
    public Statement exprHelp(Statement statement) throws AntlrException {
        extractStatement.setSelfStatement(statement);
        setExprTypes(ExprType.NIL);
        return expr();
    }
}
