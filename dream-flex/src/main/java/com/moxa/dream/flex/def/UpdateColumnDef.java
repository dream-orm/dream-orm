package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;


public class UpdateColumnDef {
    private UpdateStatement statement;

    protected UpdateColumnDef(UpdateStatement statement) {
        this.statement = statement;
    }

    public UpdateColumnDef set(ColumnDef columnDef, ColumnDef columnDef2) {
        Statement where = statement.getConditionList();
        if (where == null) {
            where = new ListColumnStatement(",");
            statement.setConditionList(where);
        }
        ListColumnStatement listColumnStatement = (ListColumnStatement) where;
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(columnDef.getStatement());
        conditionStatement.setOper(new OperStatement.EQStatement());
        conditionStatement.setRight(columnDef2.getStatement());
        listColumnStatement.add(conditionStatement);
        return this;
    }

    public UpdateColumnDef set(ColumnDef columnDef, Object value) {
        set(columnDef, new ColumnDef(new FlexMarkInvokerStatement(value)));
        return this;
    }

    public UpdateColumnDef set(ColumnDef columnDef, Query query) {
        BraceStatement braceStatement = new BraceStatement(query.getStatement());
        set(columnDef, new ColumnDef(braceStatement));
        return this;
    }

    public UpdateWhereDef where(ConditionDef conditionDef) {
        WhereStatement whereStatement = new WhereStatement();
        whereStatement.setCondition(conditionDef.getStatement());
        statement.setWhere(whereStatement);
        return new UpdateWhereDef(statement);
    }
}
