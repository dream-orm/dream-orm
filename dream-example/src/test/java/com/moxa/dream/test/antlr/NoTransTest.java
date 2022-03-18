package com.moxa.dream.test.antlr;

public class NoTransTest extends AbstractSqlTest {
    public static void main(String[] args) {
        NoTransTest noTransTest = new NoTransTest();
        noTransTest.testNoTrans();
    }

    public void testNoTrans() {
        testSqlForMany("SELECT @(top 10 a),@(nvl(a,' ')) from dual where (1+2)<3", null, null);
    }
}
