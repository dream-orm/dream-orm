//package com.dream.drive.factory;
//
//import com.dream.antlr.config.Assist;
//import com.dream.antlr.exception.AntlrException;
//import com.dream.antlr.factory.AntlrInvokerFactory;
//import com.dream.antlr.factory.InvokerFactory;
//import com.dream.antlr.invoker.Invoker;
//import com.dream.antlr.smt.InvokerStatement;
//import com.dream.antlr.smt.Statement;
//import com.dream.antlr.sql.ToMySQL;
//import com.dream.antlr.sql.ToSQL;
//import com.dream.mate.logic.inject.LogicHandler;
//import com.dream.mate.logic.invoker.LogicInvoker;
//import com.dream.mate.permission.inject.PermissionHandler;
//import com.dream.mate.permission.invoker.PermissionGetInvoker;
//import com.dream.mate.permission.invoker.PermissionInjectInvoker;
//import com.dream.mate.tenant.inject.TenantHandler;
//import com.dream.mate.tenant.invoker.TenantGetInvoker;
//import com.dream.mate.tenant.invoker.TenantInjectInvoker;
//import com.dream.mate.version.inject.VersionHandler;
//import com.dream.mate.version.invoker.CurVersionGetInvoker;
//import com.dream.mate.version.invoker.NextVersionGetInvoker;
//import com.dream.mate.version.invoker.VersionInvoker;
//import com.dream.regular.factory.CommandDialectFactory;
//import com.dream.regular.invoker.TakeMarkInvoker;
//import com.dream.regular.invoker.TakeTableInvoker;
//import com.dream.system.config.Command;
//import com.dream.system.config.MappedParam;
//import com.dream.system.config.MappedStatement;
//import com.dream.system.config.MethodInfo;
//import com.dream.system.typehandler.TypeHandlerNotFoundException;
//import com.dream.system.typehandler.factory.DefaultTypeHandlerFactory;
//import com.dream.system.typehandler.factory.TypeHandlerFactory;
//import com.dream.util.exception.DreamRunTimeException;
//
//import java.sql.Types;
//import java.util.*;
//
//public class DefaultFlexDialect implements CommandDialectFactory {
//    private TenantHandler tenantHandler;
//    private PermissionHandler permissionHandler;
//    private LogicHandler logicHandler;
//    private VersionHandler versionHandler;
//
//
//    public DefaultFlexDialect tenantHandler(TenantHandler tenantHandler) {
//        this.tenantHandler = tenantHandler;
//        return this;
//    }
//
//    public DefaultFlexDialect permissionHandler(PermissionHandler permissionHandler) {
//        this.permissionHandler = permissionHandler;
//        return this;
//    }
//
//    public DefaultFlexDialect logicHandler(LogicHandler logicHandler) {
//        this.logicHandler = logicHandler;
//        return this;
//    }
//
//    public DefaultFlexDialect versionHandler(VersionHandler versionHandler) {
//        this.versionHandler = versionHandler;
//        return this;
//    }
//
//    @Override
//    protected List<Invoker> invokerList() {
//        List<Invoker> invokerList = new ArrayList<>(4);
//        if (tenantHandler != null) {
//            invokerList.add(new TenantInjectInvoker(tenantHandler) {
//                @Override
//                public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
//                    return toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
//                }
//            });
//        }
//        if (permissionHandler != null) {
//            invokerList.add(new PermissionInjectInvoker(permissionHandler) {
//                @Override
//                public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
//                    return toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
//                }
//            });
//        }
//        if (logicHandler != null) {
//            invokerList.add(new LogicInvoker(logicHandler) {
//                @Override
//                public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
//                    return toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
//                }
//            });
//        }
//        if (versionHandler != null) {
//            invokerList.add(new VersionInvoker(versionHandler) {
//                @Override
//                public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
//                    return toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
//                }
//            });
//        }
//        return invokerList;
//    }
//
//    @Override
//    protected Invoker[] defaultInvokers() {
//        List<Invoker> invokerList = new ArrayList<>(6);
//        Invoker[] invokers = super.defaultInvokers();
//        invokerList.addAll(Arrays.asList(invokers));
//        if (tenantHandler != null) {
//            invokerList.add(new TenantGetInvoker(tenantHandler));
//        }
//        if (permissionHandler != null) {
//            invokerList.add(new PermissionGetInvoker(permissionHandler));
//        }
//        if (versionHandler != null) {
//            invokerList.add(new CurVersionGetInvoker(versionHandler));
//            invokerList.add(new NextVersionGetInvoker(versionHandler));
//        }
//        return invokerList.toArray(new Invoker[invokerList.size()]);
//    }
//
//    @Override
//    public MappedStatement compile(Command command, Statement statement, MethodInfo methodInfo) {
//        InvokerFactory invokerFactory = new AntlrInvokerFactory();
//        invokerFactory.addInvokers(defaultInvokers());
//        Assist assist = new Assist(invokerFactory, new HashMap<>());
//        String sql;
//        try {
//            sql = toSQL.toStr(statement, assist, null);
//        } catch (AntlrException e) {
//            throw new DreamRunTimeException(e);
//        }
//        TakeMarkInvoker takeMarkInvoker = (TakeMarkInvoker) assist.getInvoker(TakeMarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
//        TakeTableInvoker takeTableInvoker = (TakeTableInvoker) assist.getInvoker(TakeTableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
//        List<Object> paramList = takeMarkInvoker.getParamList();
//        List<MappedParam> mappedParamList = null;
//        if (paramList != null && !paramList.isEmpty()) {
//            mappedParamList = new ArrayList<>(paramList.size());
//            for (Object param : paramList) {
//                MappedParam mappedParam = new MappedParam();
//                try {
//                    mappedParam.setTypeHandler(typeHandlerFactory.getTypeHandler(param != null ? param.getClass() : Object.class, Types.NULL));
//                } catch (TypeHandlerNotFoundException e) {
//                    throw new DreamRunTimeException(e);
//                }
//                mappedParam.setParamValue(param);
//                mappedParamList.add(mappedParam);
//            }
//        }
//        Set<String> tableSet = takeTableInvoker.getTableSet();
//        return new MappedStatement
//                .Builder()
//                .methodInfo(methodInfo)
//                .command(command)
//                .sql(sql)
//                .tableSet(tableSet)
//                .mappedParamList(mappedParamList)
//                .build();
//    }
//}