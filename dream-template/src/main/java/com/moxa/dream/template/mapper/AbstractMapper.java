package com.moxa.dream.template.mapper;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.util.AntlrUtil;
import com.moxa.dream.system.antlr.invoker.$Invoker;
import com.moxa.dream.system.antlr.invoker.ForEachInvoker;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.table.factory.TableFactory;
import com.moxa.dream.system.util.SystemUtil;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.HashMap;
import java.util.Map;


public abstract class AbstractMapper {
    public static final String DREAM_TEMPLATE_PARAM = "dream_template_param";
    protected Session session;
    protected Map<Object, MethodInfo> methodInfoMap = new HashMap<>();

    public AbstractMapper(Session session) {
        this.session = session;
    }

    public Object execute(Class<?> type, Object arg) {
        MethodInfo methodInfo = methodInfoMap.get(type);
        if (methodInfo == null) {
            synchronized (this) {
                methodInfo = methodInfoMap.get(type);
                if (methodInfo == null) {
                    String table = getTableName(type);
                    if (ObjectUtil.isNull(table)) {
                        throw new DreamRunTimeException("类'" + type.getClass().getName() + "'未注册数据表");
                    }
                    Configuration configuration = this.session.getConfiguration();
                    TableFactory tableFactory = configuration.getTableFactory();
                    TableInfo tableInfo = tableFactory.getTableInfo(table);
                    if (tableInfo == null) {
                        throw new DreamRunTimeException("表'" + table + "'未在TableFactory注册");
                    }
                    methodInfo = getMethodInfo(configuration, tableInfo, type, arg);
                    methodInfoMap.put(type, methodInfo);
                }
            }
        }
        return execute(methodInfo, arg);
    }

    protected Object execute(MethodInfo methodInfo, Object arg) {
        return session.execute(methodInfo, wrapArg(arg));
    }

    protected Map<String, Object> wrapArg(Object arg) {
        if (arg != null) {
            if (arg instanceof Map) {
                return (Map<String, Object>) arg;
            } else {
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put(DREAM_TEMPLATE_PARAM, arg);
                return paramMap;
            }
        } else {
            return new HashMap<>();
        }
    }

    protected abstract MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, Class type, Object arg);

    protected String getTableName(Class<?> type) {
        return SystemUtil.getTableName(type);
    }

    protected String getIdWhere(TableInfo tableInfo) {
        return getIdWhere(tableInfo, false);
    }

    protected String getIdWhere(TableInfo tableInfo, boolean appendField) {
        ColumnInfo columnInfo = tableInfo.getPrimColumnInfo();
        if (columnInfo == null) {
            throw new DreamRunTimeException("表'" + tableInfo.getTable() + "'未注册主键");
        }
        String param = DREAM_TEMPLATE_PARAM;
        if (appendField) {
            param = param + "." + columnInfo.getName();
        }
        return "where " + tableInfo.getTable() + "." + columnInfo.getColumn() + "=" + AntlrUtil.invokerSQL($Invoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, param);
    }

    protected String getIdsWhere(TableInfo tableInfo) {
        ColumnInfo columnInfo = tableInfo.getPrimColumnInfo();
        if (columnInfo == null) {
            throw new DreamRunTimeException("表'" + tableInfo.getTable() + "'未注册主键");
        }
        return "where " + tableInfo.getTable() + "." + columnInfo.getColumn() + " in(" + AntlrUtil.invokerSQL(ForEachInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, DREAM_TEMPLATE_PARAM) + ")";
    }
}
