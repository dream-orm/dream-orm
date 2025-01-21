package com.dream.drive.listener;

import com.dream.system.config.MappedParam;
import com.dream.system.config.MappedStatement;
import com.dream.system.core.listener.Listener;
import com.dream.system.core.session.Session;
import com.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DebugListener implements Listener {
    final String START_DATE = "startDate";

    @Override
    public void before(MappedStatement mappedStatement, Session session) {
        mappedStatement.put(START_DATE, System.currentTimeMillis());
    }

    @Override
    public void afterReturn(Object result, MappedStatement mappedStatement, Session session) {
        after(mappedStatement);
    }

    @Override
    public void exception(Throwable e, MappedStatement mappedStatement, Session session) {
        error("异常：" + e.getMessage());
        after(mappedStatement);
    }

    public void after(MappedStatement mappedStatement) {
        List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
        List<Object> paramList;
        if (!ObjectUtil.isNull(mappedParamList)) {
            paramList = mappedParamList.stream().map(MappedParam::getParamValue).collect(Collectors.toList());
        } else {
            paramList = new ArrayList<>();
        }
        info("ID：" + mappedStatement.getId() + "\n"
                + "语句：" + mappedStatement.getSql() + "\n"
                + "参数：" + paramList + "\n"
                + "用时：" + (System.currentTimeMillis() - (long) mappedStatement.get(START_DATE)) + "ms");
    }

    protected void info(String msg) {
        System.out.println(msg);
    }

    protected void error(String msg) {
        System.err.println(msg);
    }
}
