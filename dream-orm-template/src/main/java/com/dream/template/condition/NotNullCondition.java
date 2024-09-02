package com.dream.template.condition;

import com.dream.system.util.SystemUtil;

import java.lang.reflect.Field;

public class NotNullCondition implements Condition {

    @Override
    public String getCondition(String column, Field field) {
        return SystemUtil.key(column) + " is not null";
    }
}
