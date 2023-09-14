package com.dream.helloworld.h2;


import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.PackageExpr;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToMYSQL;
import org.junit.Test;

public class HelloWorldAntlrInvokerTest {
    @Test
    public void testColonInvoker() throws AntlrException {
        Statement statement = new PackageExpr(new ExprReader("select id,name from account where id=#select")).expr();
        String s = new ToMYSQL().toStr(statement, null, null);
        System.out.println(s);
    }

}
