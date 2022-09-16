package com.moxa.dream.template.mapper;

import com.moxa.dream.system.annotation.Table;
import com.moxa.dream.system.annotation.View;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapped.MethodInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractSqlMapper implements SqlMapper {
    protected Session session;
    private Map<Class, MethodInfo> methodInfoMap = new HashMap<>();

    public AbstractSqlMapper(Session session) {
        this.session = session;
    }

    @Override
    public Object execute(Class<?> type, Object arg) {
        MethodInfo methodInfo = methodInfoMap.get(type);
        if (methodInfo == null) {
            synchronized (this) {
                methodInfo = methodInfoMap.get(type);
                if (methodInfo == null) {
                    String table = getTable(type);
                    ObjectUtil.requireNonNull(table, "类'" + type.getClass().getName() + "'未注册数据表");
                    Configuration configuration = this.session.getConfiguration();
                    TableFactory tableFactory = configuration.getTableFactory();
                    TableInfo tableInfo = tableFactory.getTableInfo(table);
                    ObjectUtil.requireNonNull(tableInfo, "表'" + table + "'未在TableFactory注册");
                    methodInfo = getMethodInfo(configuration, tableInfo, type);
                    methodInfo.compile();
                    methodInfoMap.put(type, methodInfo);
                }
            }
        }
        return session.execute(methodInfo, wrapArg(arg));
    }

    protected Object wrapArg(Object arg) {
        return arg;
    }

    protected abstract MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type);

    protected String getTable(Class<?> type) {
        if (type.isAnnotationPresent(View.class)) {
            return type.getDeclaredAnnotation(View.class).value();
        }
        if (type.isAnnotationPresent(Table.class)) {
            return type.getDeclaredAnnotation(Table.class).value();
        }
        return null;
    }
}
