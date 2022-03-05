package com.moxa.dream.test.antlr.myfucntion.simple;

import com.moxa.dream.test.antlr.AbstractSqlTest;

/**
 * 负责测试，支持系统未识别的函数
 */
public class SimpleTest extends AbstractSqlTest {
    public static void main(String[] args) {
        SimpleTest simpleTest = new SimpleTest();
        simpleTest.testSqlForMany("select decode(a,1,0,2,1,3,2,4) from dual where (1+2)*(12-12)<125", new MySimpleFunctionFactory(), null);
    }
}
