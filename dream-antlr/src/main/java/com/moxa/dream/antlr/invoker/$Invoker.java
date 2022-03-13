package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.util.ArrayList;
import java.util.List;

public class $Invoker extends AbstractInvoker {
    private List<ParamInfo> paramInfoList = new ArrayList<>();

    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        ObjectWrapper paramWrapper = assist.getCustom(ObjectWrapper.class);
        String paramName = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        Object value = paramWrapper.get(paramName);
        paramInfoList.add(new ParamInfo(paramName, value));
        return "?";
    }

    @Override
    public Handler[] handler() {
        return new Handler[0];
    }


    public List<ParamInfo> getParamInfoList() {
        return paramInfoList;
    }

    public static class ParamInfo {
        private String param;
        private Object value;

        public ParamInfo(String param, Object value) {
            this.param = param;
            this.value = value;
        }

        public String getParam() {
            return param;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}