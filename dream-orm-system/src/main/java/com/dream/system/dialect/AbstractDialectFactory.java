package com.dream.system.dialect;

import com.dream.antlr.smt.PackageStatement;
import com.dream.system.cache.CacheKey;
import com.dream.system.compile.CompileFactory;
import com.dream.system.config.Compile;
import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.inject.factory.InjectFactory;
import com.dream.system.util.SystemUtil;
import com.dream.util.exception.DreamRunTimeException;

public abstract class AbstractDialectFactory implements DialectFactory {

    @Override
    public synchronized MappedStatement compile(MethodInfo methodInfo, Object arg) throws Exception {
        switch (methodInfo.getCompile()) {
            case ANTLR_COMPILE:
                synchronized (this) {
                    Configuration configuration = methodInfo.getConfiguration();
                    if (Compile.ANTLR_COMPILE == methodInfo.getCompile()) {
                        String sql = methodInfo.getSql();
                        try {
                            PackageStatement statement = methodInfo.getStatement();
                            if (statement == null) {
                                CompileFactory compileFactory = configuration.getCompileFactory();
                                statement = compileFactory.compile(sql);
                                methodInfo.setStatement(statement);
                            }
                            CacheKey methodKey = SystemUtil.cacheKey(sql, 5, true);
                            if (methodKey != null) {
                                methodKey.update(methodInfo.getId());
                                methodInfo.setMethodKey(methodKey);
                            }
                            InjectFactory injectFactory = configuration.getInjectFactory();
                            injectFactory.inject(methodInfo, null);
                            methodInfo.setCompile(Compile.ANTLR_COMPILED);
                        } catch (Exception e) {
                            throw new DreamRunTimeException("编译方法" + methodInfo.getId() + "失败，" + e.getMessage(), e);
                        }
                    }
                }
                return compileAntlr(methodInfo, arg);
            case ANTLR_COMPILED:
                return compileAntlr(methodInfo, arg);
            default:
                return null;
        }
    }

    protected abstract MappedStatement compileAntlr(MethodInfo methodInfo, Object arg) throws Exception;

}
