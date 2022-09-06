package com.moxa.dream.drive.action;


import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.drive.session.DefaultSession;
import com.moxa.dream.drive.session.Session;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.reflection.util.NonCollection;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Properties;

public class SqlAction implements Action {
    private Configuration configuration;
    private String sql;
    private String property;
    private MethodInfo methodInfo;
    private boolean cache = true;
    private Command command = Command.NONE;

    public SqlAction(Configuration configuration, String property, String sql) {
        this.configuration = configuration;
        this.property = property;
        this.sql = sql;
    }

    @Override
    public void setProperties(Properties properties) {
        String cache = properties.getProperty("cache");
        this.cache = !String.valueOf(false).equalsIgnoreCase(cache);
        String command = properties.getProperty("command");
        if (!ObjectUtil.isNull(command)) {
            command = command.toLowerCase();
            switch (command) {
                case "query":
                    this.command = Command.QUERY;
                    break;
                case "insert":
                    this.command = Command.INSERT;
                    break;
                case "update":
                    this.command = Command.UPDATE;
                    break;
                case "delete":
                    this.command = Command.DELETE;
                    break;
                default:
                    throw new DreamRunTimeException("Command:'" + command + "'未注册");

            }
        }
    }

    @Override
    public Object doAction(Executor executor, Object arg) throws Exception {
        if (!ObjectUtil.isNull(property)) {
            if (methodInfo == null) {
                synchronized (this) {
                    if (methodInfo == null) {
                        int len;
                        Field field;
                        if ((len = property.lastIndexOf(".")) >= 0) {
                            Object target = ObjectWrapper.wrapper(arg).get(property.substring(0, len));
                            ObjectUtil.requireNonNull(target, "对象地址'" + property.substring(0, len) + "'为空");
                            field = target.getClass().getDeclaredField(property.substring(len + 1));
                        } else {
                            field = arg.getClass().getDeclaredField(property);
                        }
                        Type type = field.getGenericType();
                        methodInfo = new MethodInfo.Builder(configuration)
                                .rowType(ReflectUtil.getRowType(type))
                                .colType(ReflectUtil.getColType(type))
                                .command(command)
                                .sql(sql)
                                .build();
                    }
                }
            }
        } else {
            if (methodInfo == null) {
                synchronized (this) {
                    if (methodInfo == null) {
                        methodInfo = new MethodInfo.Builder(configuration)
                                .rowType(NonCollection.class)
                                .colType(Object.class)
                                .sql(sql)
                                .build();
                    }
                }
            }
        }
        Session session = new DefaultSession(configuration, executor) {
            @Override
            protected Command getCommand(MappedStatement mappedStatement) {
                mappedStatement.setCache(cache);
                if (SqlAction.this.command != Command.NONE) {
                    mappedStatement.setCommand(SqlAction.this.command);
                }
                return super.getCommand(mappedStatement);
            }
        };
        Object result = session.execute(methodInfo, arg);
        if (!ObjectUtil.isNull(property)) {
            ObjectWrapper.wrapper(arg).set(property, result);
        }
        return null;
    }
}
