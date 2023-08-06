package com.dream.test;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.PackageExpr;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToMYSQL;
import com.dream.system.antlr.factory.DefaultInvokerFactory;
import com.dream.util.common.ObjectWrapper;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class InvokerTest {
    @Test
    public void $InvokerTest() throws AntlrException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("a", null);
        ObjectWrapper wrapper = ObjectWrapper.wrapper(paramMap);
        Map<Class, Object> map = new HashMap<>();
        map.put(ObjectWrapper.class, wrapper);
        Assist assist = new Assist(new DefaultInvokerFactory(), map);
        String sql = "select * from dual where @not((a=@?(a)))";
        Statement statement = new PackageExpr(new ExprReader(sql)).expr();
        String realSql = new ToMYSQL().toStr(statement, assist, null);
        System.out.println(realSql);
    }
}
