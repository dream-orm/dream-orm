package com.moxa.dream.antlr.factory;

import com.moxa.dream.antlr.invoker.*;

public class AntlrInvokerFactory implements InvokerFactory {
    public static final String NAMESPACE = "antlr";
    public static final String SCAN = "scan";
    public static final String WHERE = "where";
    public static final String GROUP = "group";
    public static final String HAVING = "having";
    public static final String SORT = "sort";
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String LIMIT = "limit";
    public static final String OFFSET = "offset";
    public static final String $ = "$";
    public static final String REP = "rep";
    public static final String NON = "non";
    public static final String FOREACH = "foreach";

    @Override
    public Invoker create(String function) {
        switch (function) {
            case SCAN:
                return new ScanInvoker();
            case WHERE:
                return new WhereInvoker();
            case GROUP:
                return new GroupInvoker();
            case HAVING:
                return new HavingInvoker();
            case SORT:
                return new SortInvoker();
            case ASC:
                return new AscInvoker();
            case DESC:
                return new DescInvoker();
            case LIMIT:
                return new LimitInvoker();
            case OFFSET:
                return new OffSetInvoker();
            case REP:
                return new RepInvoker();
            case $:
                return new $Invoker();
            case NON:
                return new NonInvoker();
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
