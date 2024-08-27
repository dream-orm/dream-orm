package com.dream.system.antlr.expr;

import com.dream.antlr.config.Constant;
import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.HelperExpr;
import com.dream.antlr.expr.NoneExpr;
import com.dream.antlr.expr.SymbolExpr;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.MarkInvoker;

/**
 * 冒号语法解析器
 */
public class ColonExpr extends HelperExpr {

    private InvokerStatement statement;

    public ColonExpr(ExprReader exprReader) {
        this(exprReader, () -> new SymbolExpr(exprReader, () -> new NoneExpr(exprReader)));
    }

    public ColonExpr(ExprReader exprReader, Helper helper) {
        super(exprReader, helper);
        setExprTypes(ExprType.COLON);
    }

    @Override
    protected Statement exprColon(ExprInfo exprInfo) throws AntlrException {
        statement = new InvokerStatement();
        statement.setNamespace(MarkInvoker.DEFAULT_NAMESPACE);
        statement.setFunction(MarkInvoker.FUNCTION);
        push();
        setExprTypes(Constant.KEYWORD).addExprTypes(Constant.FUNCTION).addExprTypes(ExprType.HELP);
        return expr();
    }


    @Override
    protected Statement exprKeyWord(ExprInfo exprInfo) throws AntlrException {
        push();
        statement.setParamStatement(AntlrUtil.listColumnStatement(",", exprInfo.getInfo()));
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprFunction(ExprInfo exprInfo) throws AntlrException {
        push();
        statement.setParamStatement(AntlrUtil.listColumnStatement(",", exprInfo.getInfo()));
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        this.statement.setParamStatement(AntlrUtil.listColumnStatement(",", statement));
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    protected Statement nil() {
        return statement;
    }

}
