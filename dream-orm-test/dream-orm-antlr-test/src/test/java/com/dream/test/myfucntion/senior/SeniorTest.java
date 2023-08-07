package com.dream.test.myfucntion.senior;


import com.dream.test.AbstractSqlTest;

public class SeniorTest extends AbstractSqlTest {
    public static void main(String[] args) {
        SeniorTest seniorTest = new SeniorTest();
        seniorTest.testSqlForMany("select extract(year from a) from dual", new MySeniorFunctionFactory(), null);
    }
}
