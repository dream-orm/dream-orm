package com.moxa.dream.antlr.expr;

import com.moxa.dream.antlr.config.ExprInfo;
import com.moxa.dream.antlr.config.ExprType;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;

public abstract class HelperExpr extends SqlExpr {
    protected Helper helper;
    protected SqlExpr helpExpr;
    private boolean accept0;
    private boolean accept1;

    public HelperExpr(ExprReader exprReader, Helper helper) {
        super(exprReader);
        this.helper = helper;
        this.helpExpr = helper.helper();
        setExprTypes(ExprType.HELP, ExprType.NIL);
    }

    @Override
    protected boolean exprBefore(ExprInfo exprInfo) {
        accept0 = super.exprBefore(exprInfo);
        SqlExpr helpExpr0 = helpExpr;
        accept1 = false;
        while (true) {
            accept1 |= helpExpr0.exprBefore(exprInfo);
            if (accept1) {
                break;
            }
            if (helpExpr0 instanceof HelperExpr) {
                helpExpr0 = ((HelperExpr) helpExpr0).helper.helper();
            } else {
                break;
            }
        }
        return accept0;
    }

    @Override
    public Statement exprDefault(ExprInfo exprInfo) throws AntlrException {
        if (accept0) {
            return exprSelf(exprInfo);
        }
        if (acceptSet.contains(ExprType.HELP) && accept1) {
            Statement statement = helpExpr.expr();
            helpExpr = helper.helper();
            return exprHelp(statement);
        } else {
            return super.exprDefault(exprInfo);
        }
    }

    protected abstract Statement exprHelp(Statement statement) throws AntlrException;

    public interface Helper {
        SqlExpr helper();
    }
}
