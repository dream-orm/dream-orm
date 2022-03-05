package com.moxa.dream.test.antlr.myfucntion.simple;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.CustomFunctionStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

//手动创建Statement，手动实现翻译
public class DecodeStatement extends CustomFunctionStatement {
    @Override
    public String toString(ToSQL toSQL, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        switch (toSQL.getName()) {
            case "oracle":
                //oracle翻译
                return toORACLE(toSQL, assist, invokerList);
            default:
                //非oracle翻译
                return toOther(toSQL, assist, invokerList);
        }
    }

    protected String toORACLE(ToSQL toSQL, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        //参数不需要改变，因此提供函数名，直接翻译
        return "DECODE(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
    }

    /**
     * decode翻译case ... when ... end
     *
     * @param toSQL
     * @param assist
     * @param invokerList
     * @return
     * @throws InvokerException
     */
    protected String toOther(ToSQL toSQL, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        //获取decode参数列表
        Statement[] columnList = paramsStatement.getColumnList();
        //翻译第一个参数，拿到第一个条件字符串
        String value = toSQL.toStr(columnList[0], assist, invokerList);
        //开始手动拼接case语句
        StringBuilder builder = new StringBuilder();
        builder.append("CASE " + value + " ");
        int i;
        //遍历参数
        for (i = 1; i + 1 < columnList.length; i += 2) {
            builder.append("WHEN " + toSQL.toStr(columnList[i], assist, invokerList) + " THEN " + toSQL.toStr(columnList[i + 1], assist, invokerList) + " ");
        }
        //如果有默认值
        if (i == columnList.length - 1) {
            builder.append("ELSE " + toSQL.toStr(columnList[i], assist, invokerList));
        }
        builder.append(" END");
        //返回翻译后的语句
        return builder.toString();
    }
}