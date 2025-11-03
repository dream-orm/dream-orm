package com.dream.template.condition;

import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.util.SystemUtil;

import java.lang.reflect.Field;

public class ContainsCondition implements Condition {
    @Override
    public String getCondition(String column, Field field) {
        return SystemUtil.key(column) + " like concat('%'," + AntlrUtil.invokerSQL(MarkInvoker.FUNCTION, field.getName()) + ",'%')";
    }
}
