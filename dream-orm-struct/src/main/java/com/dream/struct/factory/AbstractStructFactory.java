package com.dream.struct.factory;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.factory.AntlrInvokerFactory;
import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.logic.inject.LogicHandler;
import com.dream.mate.logic.invoker.LogicInvoker;
import com.dream.mate.permission.inject.PermissionHandler;
import com.dream.mate.permission.invoker.PermissionGetInvoker;
import com.dream.mate.permission.invoker.PermissionInjectInvoker;
import com.dream.mate.tenant.inject.TenantHandler;
import com.dream.mate.tenant.invoker.TenantGetInvoker;
import com.dream.mate.tenant.invoker.TenantInjectInvoker;
import com.dream.struct.invoker.TakeColumnInvoker;
import com.dream.struct.invoker.TakeMarkInvoker;
import com.dream.struct.invoker.TakeTableInvoker;
import com.dream.system.config.Command;
import com.dream.system.config.MappedParam;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.DefaultTypeHandlerFactory;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.util.exception.DreamRunTimeException;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class AbstractStructFactory implements StructFactory {
    private TypeHandlerFactory typeHandlerFactory;
    private ToSQL toSQL;

    public AbstractStructFactory(ToSQL toSQL) {
        this(new DefaultTypeHandlerFactory(), toSQL);
    }

    public AbstractStructFactory(TypeHandlerFactory typeHandlerFactory, ToSQL toSQL) {
        this.typeHandlerFactory = typeHandlerFactory;
        this.toSQL = toSQL;
    }

    @Override
    public MappedStatement compile(Command command, Statement statement, MethodInfo methodInfo) {
        InvokerFactory invokerFactory = new AntlrInvokerFactory();
        invokerFactory.addInvokers(defaultInvokers());
        TenantHandler tenantHandler = tenantHandler();
        PermissionHandler permissionHandler = permissionHandler();
        LogicHandler logicHandler = logicHandler();
        List<Invoker> invokerList = new ArrayList<>(4);
        if (tenantHandler != null) {
            invokerList.add(new TenantInjectInvoker(tenantHandler) {
                @Override
                public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
                    return toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
                }
            });
            invokerFactory.addInvokers(new TenantGetInvoker(tenantHandler));
        }
        if (permissionHandler != null) {
            invokerList.add(new PermissionInjectInvoker(permissionHandler) {
                @Override
                public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
                    return toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
                }
            });
            invokerFactory.addInvokers(new PermissionGetInvoker(permissionHandler));
        }
        if (logicHandler != null) {
            invokerList.add(new LogicInvoker(logicHandler) {
                @Override
                public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
                    return toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
                }
            });
        }
        Assist assist = new Assist(invokerFactory, new HashMap<>());
        if (methodInfo != null) {
            assist.setCustom(MethodInfo.class, methodInfo);
        }
        String sql;
        try {
            sql = toSQL.toStr(statement, assist, invokerList);
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e);
        }
        TakeMarkInvoker takeMarkInvoker = (TakeMarkInvoker) assist.getInvoker(TakeMarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        TakeTableInvoker takeTableInvoker = (TakeTableInvoker) assist.getInvoker(TakeTableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        List<MappedParam> mappedParamList = takeMarkInvoker.getMappedParamList();
        if (mappedParamList != null && !mappedParamList.isEmpty()) {
            for (MappedParam mappedParam : mappedParamList) {
                try {
                    Object param = mappedParam.getParamValue();
                    mappedParam.setTypeHandler(typeHandlerFactory.getTypeHandler(param != null ? param.getClass() : Object.class, Types.NULL));
                } catch (TypeHandlerNotFoundException e) {
                    throw new DreamRunTimeException(e);
                }
            }
        }
        Set<String> tableSet = takeTableInvoker.getTableSet();
        return new MappedStatement
                .Builder()
                .methodInfo(methodInfo)
                .command(command)
                .sql(sql)
                .tableSet(tableSet)
                .mappedParamList(mappedParamList)
                .build();
    }

    protected Invoker[] defaultInvokers() {
        return new Invoker[]{new TakeMarkInvoker(), new TakeColumnInvoker(), new TakeTableInvoker()};
    }

    protected abstract TenantHandler tenantHandler();

    protected abstract PermissionHandler permissionHandler();

    protected abstract LogicHandler logicHandler();
}
