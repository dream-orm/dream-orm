package com.dream.flex.dialect;

import com.dream.antlr.smt.Statement;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.def.UpdateDef;


public interface FlexDialect {
    default SqlInfo toSQL(QueryDef queryDef) {
        return toSQL(queryDef.statement());
    }

    default SqlInfo toSQL(InsertDef insertDef) {
        return toSQL(insertDef.statement());
    }

    default SqlInfo toSQL(DeleteDef deleteDef) {
        return toSQL(deleteDef.statement());
    }

    default SqlInfo toSQL(UpdateDef updateDef) {
        return toSQL(updateDef.statement());
    }

    SqlInfo toSQL(Statement statement);
}
