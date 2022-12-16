package com.moxa.dream.template.condition;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.system.antlr.invoker.ForEachInvoker;

import static com.moxa.dream.template.mapper.AbstractMapper.DREAM_TEMPLATE_PARAM;

public class InCondition implements Condition {

    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + column + " in (" + AntlrUtil.invokerSQL(ForEachInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, DREAM_TEMPLATE_PARAM + "." + field) + ")";
    }
}
