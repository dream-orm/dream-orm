package com.dream.system.antlr.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.util.common.ObjectWrapper;

import java.util.ArrayList;
import java.util.List;

public class MarkInvoker extends AbstractInvoker {
    public static final String FUNCTION = "?";
    private final List<ParamInfo> paramInfoList = new ArrayList<>();
    ObjectWrapper paramWrapper;


    @Override
    public Invoker newInstance() {
        return new MarkInvoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        if (paramWrapper == null) {
            paramWrapper = assist.getCustom(ObjectWrapper.class);
        }
        String paramName = toSQL.toStr(invokerStatement.getParamStatement(), assist, null);
        ParamInfo paramInfo = new ParamInfo(paramName, paramWrapper.get(paramName));
        paramInfoList.add(paramInfo);
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
        private final String paramName;
        private Object paramValue;

        public ParamInfo(String paramName) {
            this(paramName, null);
        }

        public ParamInfo(String paramName, Object paramValue) {
            this.paramName = paramName;
            this.paramValue = paramValue;
        }

        public String getParamName() {
            return paramName;
        }

        public Object getParamValue() {
            return paramValue;
        }

        public void setParamValue(Object paramValue) {
            this.paramValue = paramValue;
        }
    }

}
