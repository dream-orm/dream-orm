package com.dream.antlr.expr;

import com.dream.antlr.config.Constant;
import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.MyFunctionStatement;
import com.dream.antlr.smt.Statement;

/**
 * @函数语法解析器
 */
public class InvokerExpr extends HelperExpr {

    private final InvokerStatement invokerStatement = new InvokerStatement();

    public InvokerExpr(ExprReader exprReader) {
        this(exprReader, () -> new ListColumnExpr(exprReader, new ExprInfo(ExprType.COMMA, ",")));
    }

    public InvokerExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
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
        push();
        MyFunctionStatement myFunctionStatement = (MyFunctionStatement) exprInfo.getObjInfo();
        String functionName = myFunctionStatement.getFunctionName();
        if (invokerStatement.getFunction() == null) {
            invokerStatement.setFunction(functionName);
            setExprTypes(ExprType.COLON, ExprType.LBRACE);
        } else {
            invokerStatement.setNamespace(functionName);
            setExprTypes(ExprType.LBRACE);
        }
        return expr();
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
        if (invokerStatement.getFunction() == null) {
            invokerStatement.setFunction(exprInfo.getInfo());
            setExprTypes(ExprType.COLON, ExprType.LBRACE);
        } else {
            invokerStatement.setNamespace(exprInfo.getInfo());
            setExprTypes(ExprType.LBRACE);
        }
        return expr();
    }

    @Override
    protected Statement exprColon(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(Constant.FUNCTION).addExprTypes(Constant.KEYWORD).addExprTypes(ExprType.LETTER, ExprType.MY_FUNCTION);
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
    public Statement nil() {
        return invokerStatement;
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        invokerStatement.setParamStatement(statement);
        setExprTypes(ExprType.RBRACE);
        return expr();
    }
}
