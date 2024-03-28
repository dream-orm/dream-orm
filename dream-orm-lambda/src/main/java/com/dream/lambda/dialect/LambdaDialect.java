package com.dream.lambda.dialect;

import com.dream.antlr.smt.Statement;
import com.dream.lambda.config.SqlInfo;
import com.dream.lambda.wrapper.QueryWrapper;
import com.dream.system.config.MethodInfo;

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
