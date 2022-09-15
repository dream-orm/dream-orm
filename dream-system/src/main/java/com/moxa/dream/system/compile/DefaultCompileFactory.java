package com.moxa.dream.system.compile;

import com.moxa.dream.antlr.expr.PackageExpr;
import com.moxa.dream.antlr.expr.SqlExpr;
import com.moxa.dream.antlr.factory.MyFunctionFactory;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;

public class DefaultCompileFactory implements CompileFactory {
    private MyFunctionFactory myFunctionFactory;

    public void setMyFunctionFactory(MyFunctionFactory myFunctionFactory) {
        this.myFunctionFactory = myFunctionFactory;
    }

    @Override
    public PackageStatement compile(String sql) {
        ExprReader exprReader = new ExprReader(sql, myFunctionFactory);
        SqlExpr sqlExpr = new PackageExpr(exprReader);
        PackageStatement statement = (PackageStatement) sqlExpr.expr();
        return statement;
    }

    @Override
    public CacheKey uniqueKey(String sql) {
        return uniqueKey(sql, 5);
    }

    protected CacheKey uniqueKey(String sql, int split) {
        char[] charList = sql.toCharArray();
        int index = 0;
        for (int i = 0; i < charList.length; i++) {
            char c;
            if (!Character.isWhitespace(c = charList[i])) {
                charList[index++] = Character.toLowerCase(c);
            }
        }
        if (split > index)
            split = index;
        Object[] hashObj = new Object[split];
        int len = (int) Math.ceil(index / (double) split);
        for (int i = 0; i < split; i++) {
            int sPoint = i * len;
            int size = Math.min((i + 1) * len, index) - sPoint;
            char[] tempChars = new char[size];
            System.arraycopy(charList, sPoint, tempChars, 0, size);
            hashObj[i] = new String(tempChars);
        }
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(hashObj);
        return cacheKey;
    }
}
