package com.dream.antlr.expr;

import com.dream.antlr.config.Constant;
import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.Statement;

/**
 * @函数语法解析器
 */
public class InvokerExpr extends HelperExpr {

    private final InvokerStatement invokerStatement = new InvokerStatement();

    public InvokerExpr(ExprReader exprReader, MyFunctionFactory myFunctionFactory) {
        this(exprReader, () -> new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ","), myFunctionFactory), myFunctionFactory);
    }

    public InvokerExpr(ExprReader exprReader, Helper helper, MyFunctionFactory myFunctionFactory) {
        super(exprReader, helper, myFunctionFactory);
        setExprTypes(ExprType.INVOKER);
    }

    @Override
    protected Statement exprInvoker(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(Constant.FUNCTION).addExprTypes(Constant.KEYWORD).addExprTypes(ExprType.MARK, ExprType.STAR, ExprType.LETTER, ExprType.NUMBER, ExprType.MY_FUNCTION);
        return expr();
    }

    @Override
    protected Statement exprFunction(ExprInfo exprInfo) throws AntlrException {
        return exprName(exprInfo);
    }

    @Override
    protected Statement exprMyFunction(ExprInfo exprInfo) throws AntlrException {
        return exprName(exprInfo);
    }

    @Override
    protected Statement exprKeyWord(ExprInfo exprInfo) throws AntlrException {
        return exprName(exprInfo);
    }

    @Override
    protected Statement exprLetter(ExprInfo exprInfo) throws AntlrException {
        return exprName(exprInfo);
    }

    @Override
    protected Statement exprNumber(ExprInfo exprInfo) throws AntlrException {
        return exprName(exprInfo);
    }

    @Override
    protected Statement exprMark(ExprInfo exprInfo) throws AntlrException {
        return exprName(exprInfo);
    }

    @Override
    protected Statement exprStar(ExprInfo exprInfo) throws AntlrException {
        return exprName(exprInfo);
    }

    private Statement exprName(ExprInfo exprInfo) throws AntlrException {
        push();
        invokerStatement.setFunction(exprInfo.getInfo());
        setExprTypes(ExprType.LBRACE, ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprLBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP, ExprType.RBRACE);
        return expr();
    }

    @Override
    protected Statement exprRBrace(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        invokerStatement.setParamStatement(statement);
        setExprTypes(ExprType.RBRACE);
        return expr();
    }

    @Override
    public Statement nil() {
        return invokerStatement;
    }
}
