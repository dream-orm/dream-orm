package com.dream.helloworld.h2;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.PackageExpr;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.PackageStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToMySQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.drive.factory.DefaultDriveFactory;
import com.dream.drive.factory.DriveFactory;
import com.dream.flex.def.QueryDef;
import com.dream.mate.logic.inject.LogicHandler;
import com.dream.mate.logic.inject.LogicInject;
import com.dream.mate.permission.inject.PermissionHandler;
import com.dream.mate.permission.inject.PermissionInject;
import com.dream.mate.tenant.inject.TenantHandler;
import com.dream.mate.tenant.inject.TenantInject;
import com.dream.system.antlr.factory.DefaultInvokerFactory;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.table.TableInfo;
import org.junit.Before;
import org.junit.Test;

import static com.dream.flex.def.FunctionDef.select;
import static com.dream.flex.def.FunctionDef.table;

public class HelloWorldPerformanceAntlrTest {
    private MethodInfo methodInfo;
    private ToSQL toSQL;
    private int count=10000000;
    @Before
    public void before() throws AntlrException {
        DriveFactory driveFactory = new DefaultDriveFactory(null, null, null);
        Configuration configuration = driveFactory.session().getConfiguration();
        methodInfo=new MethodInfo();
        methodInfo.setConfiguration(configuration);
        toSQL=new ToMySQL();

    }
    @Test
    public void testNonInject() throws AntlrException {
        long l=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            QueryDef queryDef=select().from(table("dual"));
            String str = toSQL.toStr(queryDef.statement(), null, null);
        }
        System.out.println(System.currentTimeMillis()-l);
    }
    @Test
    public void testInject() throws AntlrException {
        long l=System.currentTimeMillis();
        for(int i=0;i<count;i++){
            QueryDef queryDef=select().from(table("dual"));
            PackageStatement packageStatement = new PackageStatement();
            packageStatement.setStatement(queryDef.statement());
            methodInfo.setStatement(packageStatement);
            new PermissionInject(new PermissionHandler() {
                @Override
                public boolean isPermissionInject(MethodInfo methodInfo, TableInfo tableInfo) {
                    return false;
                }

                @Override
                public String getPermission(String alias) {
                    return null;
                }
            }).inject(methodInfo);
            new LogicInject(new LogicHandler() {
                @Override
                public String getLogicColumn() {
                    return null;
                }
            }).inject(methodInfo);
            new TenantInject(new TenantHandler() {
                @Override
                public Object getTenantObject() {
                    return null;
                }
            }).inject(methodInfo);
            Assist assist=new Assist(new DefaultInvokerFactory(),null);
            String str = toSQL.toStr(queryDef.statement(), assist, null);
        }
        System.out.println(System.currentTimeMillis()-l);
    }
}
