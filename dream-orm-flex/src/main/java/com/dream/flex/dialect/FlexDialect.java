package com.dream.flex.dialect;

import com.dream.antlr.smt.Statement;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.def.UpdateDef;


public interface FlexDialect {
    default SqlInfo toSQL(QueryDef queryDef) {
        return toSQL(queryDef.statement().clone());
    }

    default SqlInfo toSQL(InsertDef insertDef) {
        return toSQL(insertDef.statement().clone());
    }

    default SqlInfo toSQL(DeleteDef deleteDef) {
        return toSQL(deleteDef.statement().clone());
    }

    default SqlInfo toSQL(UpdateDef updateDef) {
        return toSQL(updateDef.statement().clone());
    }

    SqlInfo toSQL(Statement statement);
}
