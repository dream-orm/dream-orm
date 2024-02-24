package com.dream.flex.dialect;

import com.dream.antlr.smt.Statement;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.def.UpdateDef;
import com.dream.system.config.MethodInfo;

/**
 * flex方言
 */
public interface FlexDialect {
    default SqlInfo toSQL(QueryDef queryDef) {
        return toSQL(queryDef.statement().clone(), null);
    }

    default SqlInfo toSQL(InsertDef insertDef) {
        return toSQL(insertDef.statement().clone(), null);
    }

    default SqlInfo toSQL(DeleteDef deleteDef) {
        return toSQL(deleteDef.statement().clone(), null);
    }

    default SqlInfo toSQL(UpdateDef updateDef) {
        return toSQL(updateDef.statement().clone(), null);
    }

    default SqlInfo toSQL(Statement statement) {
        return toSQL(statement, null);
    }

    SqlInfo toSQL(Statement statement, MethodInfo methodInfo);
}
