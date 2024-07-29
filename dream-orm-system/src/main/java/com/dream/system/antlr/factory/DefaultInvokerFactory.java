package com.dream.system.antlr.factory;

import com.dream.antlr.factory.AntlrInvokerFactory;
import com.dream.system.antlr.invoker.*;

public class DefaultInvokerFactory extends AntlrInvokerFactory {
    public DefaultInvokerFactory() {
        addInvoker(new ScanInvoker());
        addInvoker(new MarkInvoker());
        addInvoker(new RepInvoker());
        addInvoker(new NonInvoker());
        addInvoker(new NotInvoker());
        addInvoker(new ForEachInvoker());
        addInvoker(new StarInvoker());
        addInvoker(new LimitInvoker());
        addInvoker(new OffSetInvoker());
        addInvoker(new EmitInvoker());
        addInvoker(new InsertInvoker());
        addInvoker(new InsertMapInvoker());
    }
}
