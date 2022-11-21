package com.moxa.dream.template.condition;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;

import static com.moxa.dream.template.mapper.AbstractMapper.DREAM_TEMPLATE_PARAM;

public class LeqCondition implements Condition {

    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + column + "<=" + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", DREAM_TEMPLATE_PARAM + "." + field);
    }
}
