package com.moxa.dream.module.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Join {
    String column();

    String joinColumn();

    JoinType joinType() default JoinType.LEFT_JOIN;

    enum JoinType {
        LEFT_JOIN("left join"), RIGHT_JOIN("right join"), INNER_JOIN("inner join");
        private String join;

        JoinType(String join) {
            this.join = join;
        }

        public String getJoin() {
            return join;
        }
    }
}
