package com.dream.jdbc.core;

import com.dream.system.config.*;

import java.util.ArrayList;
import java.util.List;

public class JdbcBatchMappedStatement extends BatchMappedStatement {

    public JdbcBatchMappedStatement(MethodInfo methodInfo, List<?> argList, MappedSql mappedSql) {
        super(methodInfo, argList);
        this.mappedStatementList = compile(mappedSql);
    }

    protected List<MappedStatement> compile(MappedSql mappedSql) {
        List<MappedStatement> mappedStatementList = new ArrayList<>(argList.size());
        for (Object arg : argList) {
            MappedStatement mappedStatement = new Builder().methodInfo(methodInfo).mappedSql(mappedSql).arg(arg).build();
            mappedStatementList.add(mappedStatement);
        }
        return mappedStatementList;
    }

    @Override
    protected List<MappedStatement> compile(Configuration configuration) {
        return null;
    }

    @Override
    public List<MappedParam> getMappedParamList() {
        return null;
    }
}
