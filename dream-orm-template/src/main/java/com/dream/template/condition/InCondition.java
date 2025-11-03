package com.dream.template.condition;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.ForEachInvoker;
import com.dream.system.util.SystemUtil;

import java.lang.reflect.Field;

public class InCondition implements Condition {

    @Override
    public String getCondition(String column, Field field) {
        return SystemUtil.key(column) + " in (" + AntlrUtil.invokerSQL(ForEachInvoker.FUNCTION, field.getName()) + ")";
    }
}
