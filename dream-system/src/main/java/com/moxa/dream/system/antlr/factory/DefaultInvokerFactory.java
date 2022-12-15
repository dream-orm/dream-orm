package com.moxa.dream.system.antlr.factory;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.system.antlr.invoker.*;

import java.util.HashMap;
import java.util.Map;

public class DefaultInvokerFactory extends AntlrInvokerFactory {
    public DefaultInvokerFactory() {
        addInvoker(new ScanInvoker());
        addInvoker(new $Invoker());
        addInvoker(new RepInvoker());
        addInvoker(new NonInvoker());
        addInvoker(new NotInvoker());
        addInvoker(new ForEachInvoker());
        addInvoker(new AllInvoker());
        addInvoker(new LimitInvoker());
        addInvoker(new OffSetInvoker());
        addInvoker(new TableInvoker());
    }
}
