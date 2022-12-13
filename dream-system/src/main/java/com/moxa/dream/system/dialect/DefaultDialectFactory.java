package com.moxa.dream.system.dialect;

import com.moxa.dream.antlr.factory.InvokerFactory;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;

public class DefaultDialectFactory extends AbstractRouteDialectFactory {
    private AntlrDialectFactory antlrDialectFactory = new AntlrDialectFactory();
    private UnAntlrDialectFactory unAntlrDialectFactory = new UnAntlrDialectFactory();

    public void addInvokerFactory(InvokerFactory invokerFactory) {
        antlrDialectFactory.addInvokerFactory(invokerFactory);
    }

    public <T extends InvokerFactory> T getInvokerFactory(Class<T> invokerFactoryType) {
        return antlrDialectFactory.getInvokerFactory(invokerFactoryType);
    }

    protected MappedStatement compileUnAntlr(MethodInfo methodInfo, Object arg) throws Exception {
        return unAntlrDialectFactory.compile(methodInfo, arg);
    }

    protected MappedStatement compileAntlr(MethodInfo methodInfo, Object arg) throws Exception {
        return antlrDialectFactory.compile(methodInfo, arg);
    }

    @Override
    public void addInvoker(String invokerName, Invoker invoker) {
        antlrDialectFactory.addInvoker(invokerName, invoker);
    }

    public void setDbType(DbType dbType) {
        antlrDialectFactory.setDbType(dbType);
    }

    public void setToSQL(ToSQL toSQL) {
        antlrDialectFactory.setToSQL(toSQL);
    }
}
