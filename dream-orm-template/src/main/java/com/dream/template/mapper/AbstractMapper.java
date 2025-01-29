package com.dream.template.mapper;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.ForEachInvoker;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.dialect.DialectFactory;
import com.dream.system.mapper.MapperFactory;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class AbstractMapper {
    protected final MapperFactory mapperFactory;
    protected Session session;
    protected Configuration configuration;
    protected DialectFactory dialectFactory;

    public AbstractMapper(Session session) {
        this.session = session;
        this.configuration = session.getConfiguration();
        this.dialectFactory = configuration.getDialectFactory();
        this.mapperFactory = configuration.getMapperFactory();
    }

    public Object execute(String id, Class<?> type, Object arg) {
        MethodInfo methodInfo = mapperFactory.getMethodInfo(id);
        if (methodInfo == null) {
            synchronized (this) {
                methodInfo = mapperFactory.getMethodInfo(id);
                if (methodInfo == null) {
                    String table = getTableName(type);
                    if (ObjectUtil.isNull(table)) {
                        throw new DreamRunTimeException(type.getName() + "未绑定表");
                    }
                    TableFactory tableFactory = configuration.getTableFactory();
                    TableInfo tableInfo = tableFactory.getTableInfo(table);
                    if (tableInfo == null) {
                        throw new DreamRunTimeException("表'" + table + "'未在TableFactory注册");
                    }
                    methodInfo = getMethodInfo(configuration, tableInfo, type, arg);
                    methodInfo.setId(id);
                    mapperFactory.addMethodInfo(methodInfo);
                }
            }
        }
        return execute(methodInfo, arg);
    }

    protected Object execute(MethodInfo methodInfo, Object arg) {
        MappedStatement mappedStatement;
        try {
            mappedStatement = dialectFactory.compile(methodInfo, wrapArg(arg));
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
        return execute(mappedStatement);
    }

    protected Object execute(MappedStatement mappedStatement) {
        return session.execute(mappedStatement);
    }

    protected Map<String, Object> wrapArg(Object arg) {
        if (arg != null) {
            if (arg instanceof Map) {
                return (Map<String, Object>) arg;
            } else {
                return new ObjectMap(arg);
            }
        } else {
            return new HashMap<>(4);
        }
    }

    protected abstract MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg);

    protected String getTableName(Class<?> type) {
        return SystemUtil.getTableName(type);
    }

    protected String getIdWhere(TableInfo tableInfo) {
        List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
        if (primKeys == null || primKeys.isEmpty()) {
            throw new DreamRunTimeException("表'" + tableInfo.getTable() + "'未注册主键");
        }
        return "where " + primKeys.stream().map(columnInfo -> SystemUtil.key(tableInfo.getTable()) + "." + SystemUtil.key(columnInfo.getColumn()) + "=" + AntlrUtil.invokerSQL(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, columnInfo.getName())).collect(Collectors.joining(" and "));
    }

    protected String getIdsWhere(TableInfo tableInfo) {
        List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
        if (primKeys == null || primKeys.isEmpty()) {
            throw new DreamRunTimeException("表'" + tableInfo.getTable() + "'未注册主键");
        }
        return "where " + primKeys.stream().map(columnInfo -> SystemUtil.key(tableInfo.getTable()) + "." + SystemUtil.key(columnInfo.getColumn()) + " in(" + AntlrUtil.invokerSQL(ForEachInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, "null") + ")").collect(Collectors.joining(" and "));
    }
}
