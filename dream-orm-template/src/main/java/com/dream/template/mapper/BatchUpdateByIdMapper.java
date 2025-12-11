package com.dream.template.mapper;

import com.dream.system.config.BatchMappedStatement;
import com.dream.system.config.Command;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.system.dialect.DialectFactory;
import com.dream.util.common.ObjectMap;
import com.dream.util.exception.DreamRunTimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BatchUpdateByIdMapper extends UpdateByIdMapper {

    public BatchUpdateByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected Object executeValidate(MethodInfo methodInfo, Object arg) {
        return super.execute(new BatchMappedStatement(compile(methodInfo, (List<?>) arg)));
    }

    @Override
    protected Command getCommand() {
        return Command.BATCH;
    }

    protected List<MappedStatement> compile(MethodInfo methodInfo, List<?> argList) {
        DialectFactory dialectFactory = methodInfo.getConfiguration().getDialectFactory();
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
