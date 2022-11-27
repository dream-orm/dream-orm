package com.moxa.dream.drive.listener;

import com.moxa.dream.system.config.MappedParam;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.listener.*;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DebugListener implements QueryListener, UpdateListener, InsertListener, DeleteListener, BatchListener {
    final String START_DATE = "startDate";

    @Override
    public void before(MappedStatement mappedStatement) {
        mappedStatement.put(START_DATE, System.currentTimeMillis());
        List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
        List<Object> paramList;
        if (!ObjectUtil.isNull(mappedParamList)) {
            paramList = mappedParamList.stream().map(mappedParam -> mappedParam.getParamValue()).collect(Collectors.toList());
        } else {
            paramList = new ArrayList<>();
        }
        String sql = mappedStatement.getSql();
        System.out.println("执行SQL:" + sql);
        System.out.println("执行参数:" + paramList);
    }

    @Override
    public void afterReturn(Object result, MappedStatement mappedStatement) {
        after(mappedStatement);
    }

    @Override
    public void exception(Exception e, MappedStatement mappedStatement) {
        System.err.println("执行异常：" + e.getMessage());
        after(mappedStatement);
    }

    public void after(MappedStatement mappedStatement) {
        System.out.println("执行用时：" + (System.currentTimeMillis() - (long) mappedStatement.get(START_DATE)) + "ms");
    }
}
