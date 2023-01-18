package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.FromStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;

public class FromExpr extends SqlExpr {
    private final FromStatement fromStatement = new FromStatement();

    public FromExpr(ExprReader exprReader) {
        super(exprReader);
        setExprTypes(ExprType.FROM);
    }

    @Override
    protected Statement exprFrom(ExprInfo exprInfo) throws AntlrException {
        push();
        AliasColumnExpr aliasColumnExpr = new AliasColumnExpr(exprReader);
        aliasColumnExpr.setExprTypes(ExprType.HELP);
        fromStatement.setMainTable(aliasColumnExpr.expr());
        ListColumnExpr listColumnExpr = new ListColumnExpr(exprReader, () -> new JoinExpr(exprReader), new ExprInfo(ExprType.BLANK, " "));
        listColumnExpr.addExprTypes(ExprType.NIL);
        ListColumnStatement listColumnStatement = (ListColumnStatement) listColumnExpr.expr();
        if (listColumnStatement.getColumnList().length > 0) {
            fromStatement.setJoinList(listColumnStatement);
        }
        setExprTypes(ExprType.NIL);
        return expr();
    }

    @Override
    public Statement nil() {
        return fromStatement;
    }
}
