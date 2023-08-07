package com.dream.system.annotation;

import com.dream.system.table.JoinType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义表与表之间的关联
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Join {
    /**
     * 当前表关联的字段名
     *
     * @return
     */
    String column();

    /**
     * 关联的目标表字段名
     *
     * @return
     */
    String joinColumn();

    /**
     * 关联类型
     *
     * @return
     */
    JoinType joinType() default JoinType.LEFT_JOIN;
}
