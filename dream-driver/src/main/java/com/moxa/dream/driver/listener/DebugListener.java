package com.moxa.dream.driver.listener;

import com.moxa.dream.system.core.listener.DeleteListener;
import com.moxa.dream.system.core.listener.InsertListener;
import com.moxa.dream.system.core.listener.QueryListener;
import com.moxa.dream.system.core.listener.UpdateListener;
import com.moxa.dream.system.mapped.MappedParam;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DebugListener implements QueryListener, UpdateListener, InsertListener, DeleteListener {
    static final String START_DATE = "startDate";
    @Override
    public boolean before(MappedStatement mappedStatement) {
        mappedStatement.put(START_DATE, System.currentTimeMillis());
        List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
        List<Object> paramList;
        if (!ObjectUtil.isNull(mappedParamList)) {
            paramList = mappedParamList.stream().map(mappedParam -> mappedParam.getParamValue()).collect(Collectors.toList());
        } else {
            paramList = new ArrayList<>();
        }
        String sql = mappedStatement.getSql();
        System.out.println("SQL:" + sql);
        System.out.println("PARAM:" + paramList);
        return true;
    }

    @Override
    public Object afterReturn(Object result, MappedStatement mappedStatement) {
        after(mappedStatement);
        return result;
    }

    @Override
    public void exception(Exception e, MappedStatement mappedStatement) {
        System.err.println("ERROR:" + e);
        after(mappedStatement);
    }

    public void after(MappedStatement mappedStatement) {
        System.out.println("TIME:" + (System.currentTimeMillis() - (long) mappedStatement.get(START_DATE)) + "ms");
    }
}
