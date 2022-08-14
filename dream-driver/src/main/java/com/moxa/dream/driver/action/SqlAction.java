package com.moxa.dream.driver.action;


import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.driver.session.DefaultSqlSession;
import com.moxa.dream.driver.session.SqlSession;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.Action;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.reflection.util.NonCollection;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class SqlAction implements Action {
    private Configuration configuration;

    private String sql;
    private String property;
    private Command command;
    private MethodInfo methodInfo;

    public SqlAction(Configuration configuration, String sql, String property) {
        this(configuration, sql, property, Command.NONE);
    }

    public SqlAction(Configuration configuration, String sql, String property, Command command) {
        this.configuration = configuration;
        this.sql = sql;
        this.property = property;
        this.command = command;
    }

    @Override
    public void doAction(Executor executor, Object arg) throws Exception {
        if (!ObjectUtil.isNull(property)) {
            if (arg == null) {
                throw new RuntimeException("Property 'arg' is required");
            }
            if (methodInfo == null) {
                synchronized (this) {
                    if (methodInfo == null) {
                        int len;
                        Field field;
                        if((len=property.lastIndexOf("."))>=0){
                            Object target = ObjectWrapper.wrapper(arg).get(property.substring(0, len));
                            ObjectUtil.requireNonNull(target,"对象地址'"+property.substring(0, len)+"'为空");
                            field=target.getClass().getDeclaredField(property.substring(len+1));
                        }else{
                            field = arg.getClass().getDeclaredField(property);
                        }
                        Type type = field.getGenericType();
                        methodInfo = new MethodInfo.Builder(configuration)
                                .rowType(ReflectUtil.getRowType(type))
                                .colType(ReflectUtil.getColType(type))
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
        SqlSession sqlSession = new DefaultSqlSession(configuration, executor) {
            @Override
            protected Command getCommand(MappedStatement mappedStatement) {
                Command command = mappedStatement.getCommand();
                if (command == Command.NONE && SqlAction.this.command != null) {
                    command = SqlAction.this.command;
                }
                return command;
            }
        };
        Object result = sqlSession.execute(methodInfo, arg);
        if (!ObjectUtil.isNull(property)) {
            ObjectWrapper.wrapper(arg).set(property, result);
        }
    }
}
