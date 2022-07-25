package com.moxa.dream.antlr.handler.non;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.ConditionStatement;
import com.moxa.dream.antlr.smt.OperStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.ExprUtil;

import java.util.List;

//通过解析成抽象树，知道ConditionStatement是一棵二叉树，仅仅是遍历二叉树，如果右节点为空，根据条件类型返回即可
public class ConditionHandler extends AbstractHandler {
    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        //获取条件抽象树
        ConditionStatement conditionStatement = (ConditionStatement) statement;
        //获取条件抽象树左边孩子
        Statement leftStatement = conditionStatement.getLeft();
        //获取运算抽象树
        String vl, vr;
        OperStatement operStatement = conditionStatement.getOper();
        //获取右边抽象树
        Statement rightStatement = conditionStatement.getRight();

        //switch选择运算nameId
        switch (operStatement.getNameId()) {
            case 812960120://"ANDStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vl) && ExprUtil.isEmpty(vr))
                    return null;
                else if (ExprUtil.isEmpty(vl))
                    return new SymbolStatement.LetterStatement(vr);
                else if (ExprUtil.isEmpty(vr))
                    return new SymbolStatement.LetterStatement(vl);
                else
                    return new SymbolStatement.LetterStatement(vl + " AND " + vr);
            case 759344844://"ORStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vl) && ExprUtil.isEmpty(vr))
                    return null;
                else if (ExprUtil.isEmpty(vl))
                    return new SymbolStatement.LetterStatement(vr);
                else if (ExprUtil.isEmpty(vr))
                    return new SymbolStatement.LetterStatement(vl);
                else
                    return new SymbolStatement.LetterStatement(vl + " OR " + vr);
            case 1462204615://"LTStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vr))
                    return null;
                else
                    return new SymbolStatement.LetterStatement(vl + "<" + vr);
            case -1329112489://"LEQStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vr))
                    return null;
                else
                    return new SymbolStatement.LetterStatement(vl + "<=" + vr);
            case 1857026818://"GTStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vr))
                    return null;
                else
                    return new SymbolStatement.LetterStatement(vl + ">" + vr);
            case -1974526084://"GEQStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vr))
                    return null;
                else
                    return new SymbolStatement.LetterStatement(vl + ">=" + vr);
            case 1745502755://"EQStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vr))
                    return null;
                else
                    return new SymbolStatement.LetterStatement(vl + "=" + vr);
            case -1070947051://"NEQStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vr))
                    return null;
                else
                    return new SymbolStatement.LetterStatement(vl + "<>" + vr);
            case 1921203096://"LIKEStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vr))
                    return null;
                else
                    return new SymbolStatement.LetterStatement(vl + " LIKE " + vr);
            case -557794870://"INStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                vr = toSQL.toStr(rightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(vr))
                    return null;
                else
                    return new SymbolStatement.LetterStatement(vl + " IN" + vr);
            case 689682023://"BETWEENStatement"
                vl = toSQL.toStr(leftStatement, assist, invokerList);
                ConditionStatement rightConditionStatement = (ConditionStatement) rightStatement;
                Statement rightLeftStatement = rightConditionStatement.getLeft();
                Statement rightRightStatement = rightConditionStatement.getRight();
                String rightLeft = toSQL.toStr(rightLeftStatement, assist, invokerList);
                String rightRight = toSQL.toStr(rightRightStatement, assist, invokerList);
                if (ExprUtil.isEmpty(rightLeft) && ExprUtil.isEmpty(rightRight))
                    return null;
                if (ExprUtil.isEmpty(rightLeft))
                    return new SymbolStatement.LetterStatement(vl + "<=" + rightRight);
                else if (ExprUtil.isEmpty(rightRight))
                    return new SymbolStatement.LetterStatement(vl + ">=" + rightLeft);
                else
                    return new SymbolStatement.LetterStatement(vl + " BETWEEN " + rightLeft + " AND " + rightRight);
            default:
                return statement;
        }
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof ConditionStatement;
    }
}
