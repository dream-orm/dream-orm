package com.dream.system.antlr.factory;

import com.dream.antlr.factory.AntlrInvokerFactory;
import com.dream.system.antlr.invoker.*;

public class DefaultInvokerFactory extends AntlrInvokerFactory {
    public DefaultInvokerFactory() {
        addInvoker(ScanInvoker.FUNCTION, ScanInvoker::new);
        addInvoker(MarkInvoker.FUNCTION, MarkInvoker::new);
        addInvoker(RepInvoker.FUNCTION, RepInvoker::new);
        addInvoker(NonInvoker.FUNCTION, NonInvoker::new);
        addInvoker(NotInvoker.FUNCTION, NotInvoker::new);
        addInvoker(ForEachInvoker.FUNCTION, ForEachInvoker::new);
        addInvoker(BetweenInvoker.FUNCTION, BetweenInvoker::new);
        addInvoker(StarInvoker.FUNCTION, StarInvoker::new);
        addInvoker(LimitInvoker.FUNCTION, LimitInvoker::new);
        addInvoker(OffSetInvoker.FUNCTION, OffSetInvoker::new);
        addInvoker(InsertInvoker.FUNCTION, InsertInvoker::new);
        addInvoker(InsertMapInvoker.FUNCTION, InsertMapInvoker::new);
        addInvoker(InsertMapsInvoker.FUNCTION, InsertMapsInvoker::new);
    }
}
