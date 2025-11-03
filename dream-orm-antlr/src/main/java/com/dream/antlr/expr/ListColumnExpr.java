package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

/**
 * 集合语法解析器
 */
public class ListColumnExpr extends HelperExpr {
    private final ExprType cut;
    private final ListColumnStatement listColumnStatement = new ListColumnStatement();

    public ListColumnExpr(ExprReader exprReader, ExprInfo exprInfo, MyFunctionFactory myFunctionFactory) {
        this(exprReader, () -> new CompareExpr(exprReader, myFunctionFactory), exprInfo, myFunctionFactory);
    }

    public ListColumnExpr(ExprReader exprReader, Helper helper, ExprInfo exprInfo, MyFunctionFactory myFunctionFactory) {
        super(exprReader, helper, myFunctionFactory);
        this.cut = exprInfo.getExprType();
        listColumnStatement.setCut(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
    }


    @Override
    protected Statement exprSelf(ExprInfo exprInfo) throws AntlrException {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    protected Statement exprHelp(Statement statement) throws AntlrException {
        listColumnStatement.add(statement);
        if (cut == ExprType.BLANK) {
            setExprTypes(ExprType.HELP, ExprType.NIL);
        } else {
            setExprTypes(cut, ExprType.NIL);
        }
        return expr();
    }

    @Override
    public Statement nil() {
        return listColumnStatement;
    }

}
