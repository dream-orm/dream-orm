//package com.dream.drive.factory;
//
//import com.dream.antlr.config.Assist;
//import com.dream.antlr.exception.AntlrException;
//import com.dream.antlr.invoker.Invoker;
//import com.dream.antlr.smt.InvokerStatement;
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
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class DefaultFlexDialect extends AbstractFlexDialect {
//    private TenantHandler tenantHandler;
//    private PermissionHandler permissionHandler;
//    private LogicHandler logicHandler;
//    private VersionHandler versionHandler;
//
//    public DefaultFlexDialect() {
//        this(new ToMySQL());
//    }
//
//    public DefaultFlexDialect(ToSQL toSQL) {
//        super(toSQL);
//    }
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
//}
