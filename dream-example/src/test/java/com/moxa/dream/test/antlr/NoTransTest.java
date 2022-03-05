package com.moxa.dream.test.antlr;

//使用top，不采用翻译方案，则可以使用@()作为注释,使用便失去了翻译的意义，但支持不翻译方案还是必须的
public class NoTransTest extends AbstractSqlTest {
    public static void main(String[] args) {
        NoTransTest noTransTest = new NoTransTest();
        noTransTest.testNoTrans();
    }

    public void testNoTrans() {
        testSqlForMany("SELECT @(top 10 a),@(nvl(a,' ')) from dual where (1+2)<3", null, null);
    }
}
