package com.dream.mate.version.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.util.MateUtil;
import com.dream.mate.version.invoker.CurVersionGetInvoker;
import com.dream.mate.version.invoker.NextVersionGetInvoker;
import com.dream.mate.version.invoker.VersionInvoker;

import java.util.List;

public class VersionUpdateHandler extends AbstractHandler {
    private VersionInvoker versionInvoker;

    public VersionUpdateHandler(VersionInvoker versionInvoker) {
        this.versionInvoker = versionInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        UpdateStatement updateStatement = (UpdateStatement) statement;
        SymbolStatement tableSymbolStatement = (SymbolStatement) updateStatement.getTable();
        String table = tableSymbolStatement.getValue();
        if (versionInvoker.isVersion(assist, table)) {
            ListColumnStatement columnStatement = (ListColumnStatement) updateStatement.getConditionList();
            Statement[] columnList = columnStatement.getColumnList();
            String versionColumn = versionInvoker.getVersionColumn();
            int i = 0;
            for (; i < columnList.length; i++) {
                Statement column = columnList[i];
                ConditionStatement conditionStatement = (ConditionStatement) column;
                if (versionColumn.equalsIgnoreCase(((SymbolStatement) conditionStatement.getLeft()).getValue())) {
                    conditionStatement.setRight(AntlrUtil.invokerStatement(NextVersionGetInvoker.FUNCTION));
                    break;
                }
            }
            if (i == columnList.length) {
                ConditionStatement versionConditionStatement = new ConditionStatement();
                versionConditionStatement.setLeft(new SymbolStatement.LetterStatement(versionInvoker.getVersionColumn()));
                versionConditionStatement.setOper(new OperStatement.EQStatement());
                versionConditionStatement.setRight(AntlrUtil.invokerStatement(NextVersionGetInvoker.FUNCTION));
                columnStatement.add(versionConditionStatement);
            }
            ConditionStatement whereConditionStatement = new ConditionStatement();
            whereConditionStatement.setLeft(new SymbolStatement.LetterStatement(versionInvoker.getVersionColumn()));
            whereConditionStatement.setOper(new OperStatement.EQStatement());
            whereConditionStatement.setRight(AntlrUtil.invokerStatement(CurVersionGetInvoker.FUNCTION));
            WhereStatement whereStatement = (WhereStatement) updateStatement.getWhere();
            if (whereStatement == null) {
                whereStatement = new WhereStatement();
                whereStatement.setStatement(whereConditionStatement);
                updateStatement.setWhere(whereStatement);
            } else {
                MateUtil.appendWhere(whereStatement, whereConditionStatement);
            }
            return updateStatement;
        }
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof UpdateStatement;
    }
}
