package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.template.util.TemplateUtil;
import com.moxa.dream.util.common.ObjectMap;
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
    public Object execute(Class<?> type, Object... args) {
        return execute(type, args[0]);
    }

    protected Object execute(Class<?> type, Object arg) {
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

    protected Map<String, Object> wrapArg(Object arg) {
        if (arg != null) {
            if (arg instanceof Map) {
                return (Map<String, Object>) arg;
            } else {
                return new ObjectMap(arg);
            }
        } else {
            return null;
        }
    }

    protected abstract MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type);

    protected String getTable(Class<?> type) {
        return TemplateUtil.getTable(type);
    }
}
