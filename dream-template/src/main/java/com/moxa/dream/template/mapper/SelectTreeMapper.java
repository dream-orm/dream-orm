package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.template.resulthandler.TreeResultSetHandler;

import java.util.function.Consumer;

public class SelectTreeMapper extends SelectListMapper {
    private ResultSetHandler resultSetHandler = new TreeResultSetHandler();

    public SelectTreeMapper(Session session) {
        super(session);
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg, Consumer<MappedStatement> mappedStatementConsumer) {
        methodInfo.setResultSetHandler(resultSetHandler);
        return super.execute(methodInfo, arg, mappedStatementConsumer);
    }
}
