package com.dream.template.condition;

import com.dream.system.util.SystemUtil;

public class NotNullCondition implements Condition {

    @Override
    public String getCondition(String table, String column, String field) {
        return SystemUtil.key(table) + "." + SystemUtil.key(column) + " is not null";
    }
}
