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
    static final String LOGS = "logs";
    static final String lineSeparator = System.getProperty("line.separator", "\n");

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
        StringBuilder builder = new StringBuilder();
        builder.append("SQL:" + sql).append(lineSeparator);
        builder.append("PARAM:" + paramList).append(lineSeparator);
        mappedStatement.put(LOGS, builder);
        return true;
    }

    @Override
    public Object afterReturn(Object result, MappedStatement mappedStatement) {
        after(mappedStatement);
        return result;
    }

    @Override
    public void exception(Exception e, MappedStatement mappedStatement) {
        StringBuilder builder = (StringBuilder) mappedStatement.get(LOGS);
        builder.append("ERROR:" + e).append(lineSeparator);
        after(mappedStatement);
    }

    public void after(MappedStatement mappedStatement) {
        StringBuilder builder = (StringBuilder) mappedStatement.get(LOGS);
        builder.append("TIME:" + (System.currentTimeMillis() - (long) mappedStatement.get(START_DATE)) + "ms").append(lineSeparator);
        System.out.println(builder);
    }
}
