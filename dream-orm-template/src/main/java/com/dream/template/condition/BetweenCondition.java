package com.dream.template.condition;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.BetweenInvoker;
import com.dream.system.util.SystemUtil;
import com.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Field;
import java.util.Collection;

public class BetweenCondition implements Condition {

    @Override
    public String getCondition(String column, Field field) {
        Class<?> type = field.getType();
        if (Collection.class.isAssignableFrom(type) || type.isArray()) {
            return AntlrUtil.invokerSQL(BetweenInvoker.FUNCTION, SystemUtil.key(column), field.getName());
        } else {
            throw new DreamRunTimeException("between字段类型必须是集合或数组");
        }
    }
}
