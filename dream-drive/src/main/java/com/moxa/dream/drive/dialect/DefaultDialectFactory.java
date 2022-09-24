package com.moxa.dream.drive.dialect;


import com.moxa.dream.drive.antlr.factory.DriveInvokerFactory;
import com.moxa.dream.system.dialect.SystemDialectFactory;

public class DefaultDialectFactory extends SystemDialectFactory {
    public DefaultDialectFactory() {
        addInvokerFactory(new DriveInvokerFactory());
    }
}
