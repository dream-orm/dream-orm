package com.dream.jdbc.core;

import com.dream.system.config.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JdbcBatchMappedStatement extends BatchMappedStatement {
    protected List<?> argList;

    public <T> JdbcBatchMappedStatement(MethodInfo methodInfo, List<T> argList, Command command, String sql, Set<String> tableSet) {
        super(methodInfo);
        this.argList = argList;
        this.mappedStatementList = compile(command, sql, tableSet);
    }

    protected List<MappedStatement> compile(Command command, String sql, Set<String> tableSet) {
        List<MappedStatement> mappedStatementList = new ArrayList<>(argList.size());
        for (Object arg : argList) {
            MappedStatement mappedStatement = new Builder().methodInfo(methodInfo).command(command).sql(sql).tableSet(tableSet).arg(arg).build();
            mappedStatementList.add(mappedStatement);
        }
        return mappedStatementList;
    }

    @Override
    public List<MappedParam> getMappedParamList() {
        return null;
    }
}
