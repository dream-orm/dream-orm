package com.dream.system.table;

public enum JoinType {
    LEFT_JOIN("left join"), RIGHT_JOIN("right join"), INNER_JOIN("inner join");
    private final String joinType;

    JoinType(String joinType) {
        this.joinType = joinType;
    }

    public String getJoinType() {
        return joinType;
    }
}
