package com.dream.struct.invoker;

import com.dream.antlr.smt.InvokerStatement;
import com.dream.system.config.MappedParam;

public class TakeMarkInvokerStatement extends InvokerStatement {
    private MappedParam mappedParam;

    public TakeMarkInvokerStatement(String paramName, Object paramValue) {
        MappedParam mappedParam = new MappedParam();
        mappedParam.setParamName(paramName);
        mappedParam.setParamValue(paramValue);
        this.mappedParam = mappedParam;
    }

    public int getNameId() {
        return InvokerStatement.class.getSimpleName().hashCode();
    }

    public MappedParam getMappedParam() {
        return mappedParam;
    }

    @Override
    public String getNamespace() {
        return TakeMarkInvoker.DEFAULT_NAMESPACE;
    }

    @Override
    public String getFunction() {
        return TakeMarkInvoker.FUNCTION;
    }
}
