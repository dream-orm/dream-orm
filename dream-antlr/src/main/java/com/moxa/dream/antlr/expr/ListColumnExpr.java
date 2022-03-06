package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.bind.ExprInfo;
import com.moxa.dream.antlr.bind.ExprType;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class ListColumnExpr extends HelperExpr {
    private final ExprType cut;
    private ListColumnStatement listColumnStatement = new ListColumnStatement();

    public ListColumnExpr(ExprReader exprReader,ExprInfo exprInfo){
        this(exprReader,()->new CompareExpr(exprReader),exprInfo);
    }

    public ListColumnExpr(ExprReader exprReader, Helper helper, ExprInfo exprInfo) {
        super(exprReader, helper);
        if (exprInfo == null)
            exprInfo = new ExprInfo(ExprType.BLANK, " ");
        this.cut = exprInfo.getExprType();
        listColumnStatement.setCut(new SymbolStatement.LetterStatement(exprInfo.getInfo()));
    }


    @Override
    protected Statement exprSelf(ExprInfo exprInfo) {
        push();
        setExprTypes(ExprType.HELP);
        return expr();
    }

    @Override
    public Statement exprHelp(Statement statement) {
        listColumnStatement.add(statement);
        if (cut == ExprType.BLANK)
            setExprTypes(ExprType.HELP, ExprType.NIL);
        else setExprTypes(cut, ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return listColumnStatement;
    }

}
