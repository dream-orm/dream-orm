package com.dream.antlr.expr;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.FromStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;

/**
 * from语法解析器
 */
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
