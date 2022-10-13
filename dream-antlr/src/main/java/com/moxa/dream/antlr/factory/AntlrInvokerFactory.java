package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.invoker.*;

public class AntlrInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "antlr";
    public static final String SCAN = "scan";
    public static final String $ = "$";
    public static final String REP = "rep";
    public static final String NON = "non";
    public static final String NOT = "not";
    public static final String FOREACH = "foreach";

    @Override
    public Invoker create(String function) {
        switch (function) {
            case SCAN:
                return new ScanInvoker();
            case REP:
                return new RepInvoker();
            case $:
                return new $Invoker();
            case NON:
                return new NonInvoker();
            case NOT:
                return new NotInvoker();
            case FOREACH:
                return new ForEachInvoker();
            default:
                return null;
        }
    }

    @Override
    public final String namespace() {
        return NAMESPACE;
    }
}
