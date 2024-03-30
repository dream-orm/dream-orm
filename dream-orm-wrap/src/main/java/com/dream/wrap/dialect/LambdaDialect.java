package com.dream.wrap.dialect;

import com.dream.antlr.smt.Statement;
import com.dream.system.config.MethodInfo;
import com.dream.wrap.config.SqlInfo;
import com.dream.wrap.wrapper.QueryWrapper;

/**
 * flex方言
 */
public interface LambdaDialect {
    default SqlInfo toSQL(QueryWrapper queryWrapper) {
        return toSQL(queryWrapper.statement().clone(), null);
    }
//
//    default SqlInfo toSQL(InsertWrapper insertWrapper) {
//        return toSQL(insertWrapper.statement().clone(), null);
//    }
//
//    default SqlInfo toSQL(DeleteWrapper deleteWrapper) {
//        return toSQL(deleteWrapper.statement().clone(), null);
//    }
//
//    default SqlInfo toSQL(UpdateWrapper updateWrapper) {
//        return toSQL(updateWrapper.statement().clone(), null);
//    }

    default SqlInfo toSQL(Statement statement) {
        return toSQL(statement, null);
    }

    SqlInfo toSQL(Statement statement, MethodInfo methodInfo);
}
