package com.dream.helloworld.h2;


import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.config.ExprType;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.*;
import com.dream.antlr.factory.MyFunctionFactory;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.Statement;
import org.junit.Test;

public class HelloWorldStudyTest {
    private MyFunctionFactory myFunctionFactory;

    @Test
    public void testQuery() {
        ExprReader exprReader = new ExprReader("select name,avg(age) from users where dept_id=1001 group by name");
        ExprInfo exprInfo;
        while (true) {
            exprInfo = exprReader.push();
            if (exprInfo.getExprType() == ExprType.ACC) {
                break;
            }
            System.out.println("单词：" + exprInfo.getInfo() + "\t\t单词类型：" + exprInfo.getExprType());
        }
    }

    @Test
    public void testSelect() throws AntlrException {
        ExprReader exprReader = new ExprReader("select name,avg(age)");
        Statement statement = new SelectExpr(exprReader, myFunctionFactory).expr();
    }

    @Test
    public void testFrom() throws AntlrException {
        ExprReader exprReader = new ExprReader("from users");
        Statement statement = new FromExpr(exprReader, myFunctionFactory).expr();
    }

    @Test
    public void testWhere() throws AntlrException {
        ExprReader exprReader = new ExprReader("where dept_id=1001");
        Statement statement = new WhereExpr(exprReader, myFunctionFactory).expr();
    }

    @Test
    public void testGroup() throws AntlrException {
        ExprReader exprReader = new ExprReader("group by name");
        Statement statement = new GroupExpr(exprReader, myFunctionFactory).expr();
    }

    @Test
    public void testFunc() throws AntlrException {
        ExprReader exprReader = new ExprReader("avg(age)");
        Statement statement = new FunctionExpr(exprReader, myFunctionFactory).expr();
    }

    @Test
    public void testCompare() throws AntlrException {
        ExprReader exprReader = new ExprReader("dept_id=1001");
        Statement statement = new CompareExpr(exprReader, myFunctionFactory).expr();
    }
}
