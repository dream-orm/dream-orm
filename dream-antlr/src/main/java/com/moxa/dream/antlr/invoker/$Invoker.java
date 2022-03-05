package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.util.wrapper.ObjectWrapper;

import java.util.ArrayList;
import java.util.List;

public class $Invoker extends AbstractInvoker {
    private List<ParamInfo> paramInfoList = new ArrayList<>();
    private ObjectWrapper paramWrapper;

    @Override
    public void init(ToAssist assist) {
        paramWrapper = assist.getCustom(ObjectWrapper.class);
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = invokerStatement.getListColumnStatement().getColumnList();
        if (columnList.length != 1)
            throw new InvokerException("参数个数错误,不满足@" + AntlrInvokerFactory.$ + ":" + AntlrInvokerFactory.NAMESPACE + "(value)");
        String paramName = toSQL.toStr(columnList[0], assist, invokerList);
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
    }
}