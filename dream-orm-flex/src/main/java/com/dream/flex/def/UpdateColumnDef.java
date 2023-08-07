package com.dream.flex.def;

import com.dream.antlr.smt.*;
import com.dream.flex.invoker.FlexMarkInvokerStatement;


public interface UpdateColumnDef<UpdateColumn extends UpdateColumnDef, UpdateWhere extends UpdateWhereDef> extends Update {
    default UpdateColumn set(ColumnDef columnDef, ColumnDef columnDef2) {
        Statement where = statement().getConditionList();
        if (where == null) {
            where = new ListColumnStatement(",");
            statement().setConditionList(where);
        }
        ListColumnStatement listColumnStatement = (ListColumnStatement) where;
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(columnDef.getStatement());
        conditionStatement.setOper(new OperStatement.EQStatement());
        conditionStatement.setRight(columnDef2.getStatement());
        listColumnStatement.add(conditionStatement);
        return (UpdateColumn) this;
    }

    default UpdateColumn set(ColumnDef columnDef, Object value) {
        set(columnDef, new ColumnDef(new FlexMarkInvokerStatement(value)));
        return (UpdateColumn) this;
    }

    default UpdateColumn set(ColumnDef columnDef, Query query) {
        BraceStatement braceStatement = new BraceStatement(query.statement());
        set(columnDef, new ColumnDef(braceStatement));
        return (UpdateColumn) this;
    }

    default UpdateWhere where(ConditionDef conditionDef) {
        WhereStatement whereStatement = new WhereStatement();
        whereStatement.setCondition(conditionDef.getStatement());
        statement().setWhere(whereStatement);
        return (UpdateWhere) creatorFactory().newUpdateWhereDef(statement());
    }
}
