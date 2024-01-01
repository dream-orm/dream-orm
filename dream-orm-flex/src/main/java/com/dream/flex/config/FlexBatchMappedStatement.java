package com.dream.flex.config;

import com.dream.system.config.BatchMappedStatement;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;

import java.util.List;

public class FlexBatchMappedStatement extends BatchMappedStatement {
    public FlexBatchMappedStatement(MethodInfo methodInfo, List<MappedStatement> mappedStatementList) {
        super(methodInfo);
        this.mappedStatementList = mappedStatementList;
    }
}
