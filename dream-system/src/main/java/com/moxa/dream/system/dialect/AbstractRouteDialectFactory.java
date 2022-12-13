package com.moxa.dream.system.dialect;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.compile.CompileFactory;
import com.moxa.dream.system.config.Compile;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.inject.factory.InjectFactory;
import com.moxa.dream.util.exception.DreamRunTimeException;

public abstract class AbstractRouteDialectFactory implements DialectFactory {

    @Override
    public synchronized MappedStatement compile(MethodInfo methodInfo, Object arg) throws Exception {
        switch (methodInfo.getCompile()) {
            case UN_ANTLR_COMPILE:
                return compileUnAntlr(methodInfo, arg);
            case ANTLR_COMPILE:
                synchronized (this) {
                    if (Compile.ANTLR_COMPILE == methodInfo.getCompile()) {
                        Configuration configuration = methodInfo.getConfiguration();
                        String sql = methodInfo.getSql();
                        try {
                            CompileFactory compileFactory = configuration.getCompileFactory();
                            PackageStatement statement = compileFactory.compile(sql);
                            methodInfo.setStatement(statement);
                            CacheKey methodKey = compileFactory.uniqueKey(sql);
                            if (methodKey != null) {
                                methodKey.update(new Object[]{methodInfo.getColType(), methodInfo.getRowType()});
                            }
                            methodInfo.setMethodKey(methodKey);
                            InjectFactory injectFactory = configuration.getInjectFactory();
                            injectFactory.inject(methodInfo);
                            methodInfo.setCompile(Compile.ANTLR_COMPILED);
                        } catch (Exception e) {
                            throw new DreamRunTimeException("编译方法" + methodInfo.getId() + "失败，" + e.getMessage(), e);
                        }
                    }
                }
            case ANTLR_COMPILED:
                return compileAntlr(methodInfo, arg);
            default:
                return null;
        }
    }

    protected abstract MappedStatement compileUnAntlr(MethodInfo methodInfo, Object arg) throws Exception;

    protected abstract MappedStatement compileAntlr(MethodInfo methodInfo, Object arg) throws Exception;

}
