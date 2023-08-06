package com.dream.flex.inject;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.flex.invoker.FlexMarkInvoker;
import com.dream.flex.invoker.FlexTableInvoker;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.Inject;

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
