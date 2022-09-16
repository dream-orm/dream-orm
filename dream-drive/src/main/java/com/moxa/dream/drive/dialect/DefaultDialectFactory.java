package com.moxa.dream.drive.dialect;


import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.drive.antlr.factory.DriveInvokerFactory;
import com.moxa.dream.system.dialect.SystemDialectFactory;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Arrays;

public class DefaultDialectFactory extends SystemDialectFactory {
    public DefaultDialectFactory() {
        invokerFactoryList.add(new DriveInvokerFactory());
    }

    public void addInvokerFactoryList(InvokerFactory[] invokerFactories) {
        if (!ObjectUtil.isNull(invokerFactories)) {
            invokerFactoryList.addAll(Arrays.asList(invokerFactories));
        }
    }
}
