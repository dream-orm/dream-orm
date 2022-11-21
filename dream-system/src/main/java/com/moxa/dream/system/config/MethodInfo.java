package com.moxa.dream.system.config;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.compile.CompileFactory;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.inject.factory.InjectFactory;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MethodInfo {
    protected final Map<Class, Object> builtMap = new HashMap<>();
    protected Configuration configuration;
    protected String name;
    protected Class<? extends Collection> rowType;
    protected Class colType;
    protected String[] columnNames;
    protected boolean cache = true;
    protected Command command = Command.NONE;
    protected String sql;
    protected int timeOut;
    protected PackageStatement statement;
    protected CacheKey methodKey;
    protected Method method;
    protected Action[] initActionList;
    protected Action[] loopActionList;
    protected Action[] destroyActionList;
    protected MethodParam[] methodParamList;
    protected boolean compile = false;

    protected MethodInfo() {

    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Collection> getRowType() {
        return rowType;
    }

    public Class getColType() {
        return colType;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public boolean isCache() {
        return cache;
    }

    public Command getCommand() {
        return command;
    }

    public String getSql() {
        return sql;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public Action[] getInitActionList() {
        return initActionList;
    }

    public Action[] getLoopActionList() {
        return loopActionList;
    }

    public Action[] getDestroyActionList() {
        return destroyActionList;
    }

    public MethodParam[] getMethodParamList() {
        return methodParamList;
    }

    public PackageStatement getStatement() {
        return statement;
    }

    public CacheKey getMethodKey() {
        return methodKey.clone();
    }

    public Method getMethod() {
        return method;
    }

    public boolean isCompile() {
        return compile;
    }

    public String getId() {
        if (method == null) {
            return "";
        } else {
            return method.getDeclaringClass().getName() + "." + method.getName();
        }
    }

    public <T> void set(Class<T> type, T value) {
        builtMap.put(type, value);
    }

    public <T> T get(Class<T> type) {
        return (T) builtMap.get(type);
    }

    public synchronized void compile() {
        if (!compile) {
            CompileFactory compileFactory = configuration.getCompileFactory();
            this.statement = compileFactory.compile(sql);
            CacheKey uniqueKey = compileFactory.uniqueKey(sql);
            if (uniqueKey != null) {
                uniqueKey.update(new Object[]{colType, rowType});
            }
            this.methodKey = uniqueKey;
            InjectFactory injectFactory = configuration.getInjectFactory();
            injectFactory.inject(this);
            compile = true;
        }
    }

    public static class Builder {
        protected final MethodInfo methodInfo;

        public Builder(Configuration configuration) {
            methodInfo = new MethodInfo();
            methodInfo.configuration = configuration;
        }

        public Builder name(String name) {
            methodInfo.name = name;
            return this;
        }

        public Builder rowType(Class<? extends Collection> rowType) {
            methodInfo.rowType = rowType;
            return this;
        }

        public Builder colType(Class colType) {
            methodInfo.colType = colType;
            return this;
        }

        public Builder columnNames(String[] columnNames) {
            methodInfo.columnNames = columnNames;
            return this;
        }

        public Builder cache(boolean cache) {
            methodInfo.cache = cache;
            return this;
        }

        public Builder command(Command command) {
            methodInfo.command = command;
            return this;
        }

        public Builder methodParamList(MethodParam[] methodParamList) {
            methodInfo.methodParamList = methodParamList;
            return this;
        }

        public Builder sql(String sql) {
            methodInfo.sql = sql;
            return this;
        }

        public Builder timeOut(int timeOut) {
            methodInfo.timeOut = timeOut;
            return this;
        }

        public Builder initActionList(Action[] actionList) {
            methodInfo.initActionList = actionList;
            return this;
        }

        public Builder loopActionList(Action[] actionList) {
            methodInfo.loopActionList = actionList;
            return this;
        }

        public Builder destroyActionList(Action[] actionList) {
            methodInfo.destroyActionList = actionList;
            return this;
        }

        public Builder method(Method method) {
            methodInfo.method = method;
            return this;
        }

        protected boolean isValid() {
            return !ObjectUtil.isNull(methodInfo.getSql());
        }

        public MethodInfo build() {
            if (isValid()) {
                return methodInfo;
            } else {
                return null;
            }
        }
    }
}
