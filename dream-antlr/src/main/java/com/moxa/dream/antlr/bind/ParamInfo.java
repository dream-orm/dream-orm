package com.moxa.dream.antlr.bind;

import com.moxa.dream.antlr.factory.InvokerFactory;

import java.util.Map;

public class ParamInfo {
    private Map<String, InvokerFactory> invokerFactoryMap;
    private Object param;

    public ParamInfo() {
    }

    public ParamInfo(Map<String, InvokerFactory> invokerFactoryMap, Object param) {
        this.invokerFactoryMap = invokerFactoryMap;
        this.param = param;
    }


    public Map<String, InvokerFactory> getInvokerFactoryMap() {
        return invokerFactoryMap;
    }

    public Object getParam() {
        return param;
    }
}
