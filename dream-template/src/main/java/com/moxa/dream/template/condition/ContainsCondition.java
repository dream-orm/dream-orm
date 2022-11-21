package com.moxa.dream.template.condition;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;

import static com.moxa.dream.template.mapper.AbstractMapper.DREAM_TEMPLATE_PARAM;

public class ContainsCondition implements Condition {
    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + column + " like concat('%'," + InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$, ",", DREAM_TEMPLATE_PARAM + "." + field) + ",'%')";
    }
}
