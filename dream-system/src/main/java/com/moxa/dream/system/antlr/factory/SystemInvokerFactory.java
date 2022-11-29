package com.moxa.dream.system.antlr.factory;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.system.antlr.invoker.*;

public class SystemInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "system";
    public static final String SCAN = "scan";
    public static final String $ = "$";
    public static final String REP = "rep";
    public static final String NON = "non";
    public static final String NOT = "not";
    public static final String FOREACH = "foreach";
    public static final String ALL = "all";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String TABLE = "table";


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
            case ALL:
                return new AllInvoker();
            case LIMIT:
                return new LimitInvoker();
            case OFFSET:
                return new OffSetInvoker();
            case TABLE:
                return new TableInvoker();
            default:
                return null;
        }
    }

    @Override
    public String namespace() {
        return NAMESPACE;
    }
}
