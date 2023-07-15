package com.moxa.dream.flex.inject;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.flex.invoker.FlexMarkInvoker;
import com.moxa.dream.flex.invoker.FlexTableInvoker;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.Inject;

public class FlexInject implements Inject {
    @Override
    public void inject(MethodInfo methodInfo) {
        InvokerFactory invokerFactory = methodInfo.getConfiguration().getInvokerFactory();
        if (invokerFactory.getInvoker(FlexTableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new FlexTableInvoker());
        }
        if (invokerFactory.getInvoker(FlexMarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE) == null) {
            invokerFactory.addInvokers(new FlexMarkInvoker());
        }
    }
}
