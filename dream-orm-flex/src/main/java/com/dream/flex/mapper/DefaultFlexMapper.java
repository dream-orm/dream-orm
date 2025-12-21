package com.dream.flex.mapper;

import com.dream.antlr.factory.InvokerFactory;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.invoker.TakeMarkInvoker;
import com.dream.system.config.*;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.resultsethandler.SimpleResultSetHandler;
import com.dream.system.core.session.Session;
import com.dream.system.dialect.DialectFactory;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;

import java.util.*;

public class DefaultFlexMapper implements FlexMapper {
    private final Session session;
    private final Configuration configuration;
    private final DialectFactory dialectFactory;
    private final ResultSetHandler resultSetHandler;

    public DefaultFlexMapper(Session session) {
        this(session, new SimpleResultSetHandler());
    }

    public DefaultFlexMapper(Session session, ResultSetHandler resultSetHandler) {
        this.session = session;
        Configuration configuration = session.getConfiguration();
        this.configuration = configuration;
        this.dialectFactory = configuration.getDialectFactory();
        this.resultSetHandler = resultSetHandler;
        InvokerFactory invokerFactory = configuration.getInvokerFactory();
        Invoker invoker = invokerFactory.getInvoker(TakeMarkInvoker.FUNCTION);
        if (invoker == null) {
            invokerFactory.addInvoker(TakeMarkInvoker.FUNCTION, TakeMarkInvoker::new);
        }
    }

    @Override
    public <T> T selectOne(QueryDef queryDef, Class<T> type) {
        MappedStatement mappedStatement = compile(NonCollection.class, type, queryDef.statement());
        return (T) session.execute(mappedStatement);
    }

    @Override
    public <T> List<T> selectList(QueryDef queryDef, Class<T> type) {
        MappedStatement mappedStatement = compile(List.class, type, queryDef.statement());
        return (List<T>) session.execute(mappedStatement);
    }

    @Override
    public <T> Page<T> selectPage(QueryDef queryDef, Class<T> type, Page page) {
        MethodInfo methodInfo = methodInfo(List.class, type, queryDef.statement());
        String PAGE = "page";
        methodInfo.setPage(PAGE);
        Map paramMap = new HashMap();
        paramMap.put(PAGE, page);
        MappedStatement mappedStatement;
        try {
            mappedStatement = dialectFactory.compile(methodInfo, paramMap);
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
        List<T> rows = (List<T>) session.execute(mappedStatement);
        page.setRows(rows);
        return page;
    }

    @Override
    public int execute(UpdateDef updateDef) {
        MappedStatement mappedStatement = compile(NonCollection.class, Integer.class, updateDef.statement());
        return (int) session.execute(mappedStatement);
    }

    @Override
    public List<int[]> execute(UpdateDef[] updateDefList, int batchSize) {
        if (ObjectUtil.isNull(updateDefList)) {
            return null;
        }
        Statement[] statementList = new Statement[updateDefList.length];
        for (int i = 0; i < updateDefList.length; i++) {
            statementList[i] = updateDefList[i].statement();
        }
        return batch(statementList, batchSize);
    }

    @Override
    public int execute(DeleteDef deleteDef) {
        MappedStatement mappedStatement = compile(NonCollection.class, Integer.class, deleteDef.statement());
        return (int) session.execute(mappedStatement);
    }

    @Override
    public int execute(InsertDef insertDef) {
        MappedStatement mappedStatement = compile(NonCollection.class, Integer.class, insertDef.statement());
        return (int) session.execute(mappedStatement);
    }

    @Override
    public List<int[]> execute(InsertDef[] insertDefList, int batchSize) {
        if (ObjectUtil.isNull(insertDefList)) {
            return null;
        }
        Statement[] statementList = new Statement[insertDefList.length];
        for (int i = 0; i < insertDefList.length; i++) {
            statementList[i] = insertDefList[i].statement();
        }
        return batch(statementList, batchSize);
    }

    private List<int[]> batch(Statement[] statementList, int batchSize) {
        List<MappedStatement> mappedStatementList = new ArrayList<>(statementList.length);
        for (Statement statement : statementList) {
            MethodInfo methodInfo = methodInfo(NonCollection.class, Integer[].class, statement);
            try {
                MappedStatement mappedStatement = dialectFactory.compile(methodInfo, new HashMap<>());
                mappedStatementList.add(mappedStatement);
            } catch (Exception e) {
                throw new DreamRunTimeException(e);
            }
        }
        BatchMappedStatement batchMappedStatement = new BatchMappedStatement(mappedStatementList);
        batchMappedStatement.setBatchSize(batchSize);
        return (List<int[]>) session.execute(batchMappedStatement);
    }

    @Override
    public boolean exists(QueryDef queryDef) {
        QueryStatement statement = queryDef.statement();
        SelectStatement selectStatement = statement.getSelectStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement();
        listColumnStatement.add(new SymbolStatement.NumberStatement("1"));
        selectStatement.setSelectList(listColumnStatement);
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new SymbolStatement.NumberStatement("1"));
        limitStatement.setSecond(new SymbolStatement.NumberStatement("0"));
        statement.setLimitStatement(limitStatement);
        MappedStatement mappedStatement = compile(NonCollection.class, Integer.class, statement);
        Integer result = (Integer) session.execute(mappedStatement);
        return result != null;
    }

    protected MethodInfo methodInfo(Class<? extends Collection> rowType, Class<?> colType, Statement statement) {
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setConfiguration(configuration);
        methodInfo.setResultSetHandler(resultSetHandler);
        methodInfo.setRowType(rowType);
        methodInfo.setColType(colType);
        if (statement instanceof PackageStatement) {
            methodInfo.setStatement((PackageStatement) statement);
        } else {
            PackageStatement packageStatement = new PackageStatement();
            packageStatement.setStatement(statement);
            methodInfo.setStatement(packageStatement);
        }
        return methodInfo;
    }

    protected MappedStatement compile(Class<? extends Collection> rowType, Class<?> colType, Statement statement) {
        MethodInfo methodInfo = methodInfo(rowType, colType, statement);
        try {
            return dialectFactory.compile(methodInfo, new HashMap<>());
        } catch (Exception e) {
            throw new DreamRunTimeException(e);
        }
    }
}
