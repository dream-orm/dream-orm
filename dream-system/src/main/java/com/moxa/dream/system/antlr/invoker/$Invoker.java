package com.moxa.dream.system.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.common.ObjectWrapper;

import java.util.ArrayList;
import java.util.List;

public class $Invoker extends AbstractInvoker {
    public static final String FUNCTION = "$";
    private final List<ParamInfo> paramInfoList = new ArrayList<>();
    ObjectWrapper paramWrapper;

    @Override
    public void init(Assist assist) {
        paramWrapper = assist.getCustom(ObjectWrapper.class);
    }

    @Override
    public Invoker newInstance() {
        return new $Invoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
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
