package com.dream.system.config;

import com.dream.system.dialect.DialectFactory;
import com.dream.util.common.ObjectMap;
import com.dream.util.exception.DreamRunTimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AntlrBatchMappedStatement extends BatchMappedStatement<AntlrBatchMappedStatement> {
    protected List<?> argList;


    public AntlrBatchMappedStatement(MethodInfo methodInfo, List<?> argList) {
        super(methodInfo);
        this.argList = argList;
        mappedStatementList = compile(methodInfo.getConfiguration());
    }

    protected List<MappedStatement> compile(Configuration configuration) {
        DialectFactory dialectFactory = configuration.getDialectFactory();
        List<MappedStatement> mappedStatementList = new ArrayList<>(argList.size());
        for (Object arg : argList) {
            Map<String, Object> argMap;
            if (arg instanceof Map) {
                argMap = (Map<String, Object>) arg;
            } else {
                argMap = new ObjectMap(arg);
            }
            try {
                mappedStatementList.add(dialectFactory.compile(methodInfo, argMap));
            } catch (Exception e) {
                throw new DreamRunTimeException("抽象树方法" + methodInfo.getId() + "翻译失败，" + e.getMessage(), e);
            }
        }
        return mappedStatementList;
    }
}
