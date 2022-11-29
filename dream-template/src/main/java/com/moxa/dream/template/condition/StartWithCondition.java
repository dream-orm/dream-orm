package com.moxa.dream.template.condition;

import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.util.InvokerUtil;

import static com.moxa.dream.template.mapper.AbstractMapper.DREAM_TEMPLATE_PARAM;

public class StartWithCondition implements Condition {

    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + column + " like concat('%'," + InvokerUtil.wrapperInvokerSQL(SystemInvokerFactory.NAMESPACE, SystemInvokerFactory.$, ",", DREAM_TEMPLATE_PARAM + "." + field) + ")";
    }
}
