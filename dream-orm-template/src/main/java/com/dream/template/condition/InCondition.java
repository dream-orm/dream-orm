package com.dream.template.condition;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.ForEachInvoker;
import com.dream.system.util.SystemUtil;

public class InCondition implements Condition {

    @Override
    public String getCondition(String table, String column, String field) {
        return SystemUtil.key(table) + "." + SystemUtil.key(column) + " in (" + AntlrUtil.invokerSQL(ForEachInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, field) + ")";
    }
}
