package com.dream.template.condition;

import com.dream.system.util.SystemUtil;

public class NotNullCondition implements Condition {

    @Override
    public String getCondition(String table, String column, String field) {
        return SystemUtil.transfer(table) + "." + SystemUtil.transfer(column) + " is not null";
    }
}
