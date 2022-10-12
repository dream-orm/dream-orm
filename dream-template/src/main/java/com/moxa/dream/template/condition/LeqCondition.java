package com.moxa.dream.template.condition;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.util.InvokerUtil;

public class LeqCondition implements Condition {
    private final String param="param";
    @Override
    public String getCondition(String table, String column, String field) {
        return table + "." + column + "<="+ InvokerUtil.wrapperInvokerSQL(AntlrInvokerFactory.NAMESPACE,AntlrInvokerFactory.$,",",param+"."+field);
    }
}
