package com.dream.stream.mapper;

import com.dream.antlr.smt.*;
import com.dream.stream.wrapper.QueryWrapper;
import com.dream.struct.factory.StructFactory;
import com.dream.struct.invoker.TakeMarkInvokerStatement;
import com.dream.system.config.*;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.resultsethandler.SimpleResultSetHandler;
import com.dream.system.core.session.Session;
import com.dream.util.common.NonCollection;
import com.dream.util.exception.DreamRunTimeException;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class DefaultStreamMapper implements StreamMapper {
    private Session session;
    private Configuration configuration;
    private boolean offset = true;
    private StructFactory dialectFactory;
    private ResultSetHandler resultSetHandler;
    private Consumer<MethodInfo> consumer;

    public DefaultStreamMapper(Session session, StructFactory dialectFactory) {
        this(session, dialectFactory, new SimpleResultSetHandler());
    }

    public DefaultStreamMapper(Session session, StructFactory dialectFactory, ResultSetHandler resultSetHandler) {
        this.session = session;
        this.dialectFactory = dialectFactory;
        this.resultSetHandler = resultSetHandler;
        this.configuration = session.getConfiguration();
    }

    @Override
    public StreamMapper useStruct(StructFactory dialectFactory) {
        return new DefaultStreamMapper(session, dialectFactory, resultSetHandler);
    }

    @Override
    public StreamMapper useMethodInfo(Consumer<MethodInfo> consumer) {
        DefaultStreamMapper streamMapper = new DefaultStreamMapper(session, dialectFactory, resultSetHandler);
        streamMapper.consumer = consumer;
        return streamMapper;
    }

    @Override
    public <T> T selectOne(QueryWrapper<T> queryWrapper) {
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, queryWrapper.entityType());
        MappedStatement mappedStatement = dialectFactory.compile(Command.QUERY, queryWrapper.statement(), methodInfo);
        return (T) session.execute(mappedStatement);
    }

    @Override
    public <T> List<T> selectList(QueryWrapper<T> queryWrapper) {
        MethodInfo methodInfo = getMethodInfo(List.class, queryWrapper.entityType());
        MappedStatement mappedStatement = dialectFactory.compile(Command.QUERY, queryWrapper.statement(), methodInfo);
        return (List<T>) session.execute(mappedStatement);
    }

    @Override
    public <T> Page<T> selectPage(QueryWrapper<T> queryWrapper, Page<T> page) {
        QueryStatement statement = queryWrapper.statement();
        if (page.getTotal() == 0) {
            MethodInfo countMethodInfo = getMethodInfo(NonCollection.class, Long.class);
            MappedStatement countMappedStatement = dialectFactory.compile(Command.QUERY, countQueryStatement(queryWrapper.statement().clone()), countMethodInfo);
            page.setTotal((long) session.execute(countMappedStatement));
        }
        MethodInfo methodInfo = getMethodInfo(Collection.class, queryWrapper.entityType());
        QueryStatement queryStatement = pageQueryStatement(statement, page.getStartRow(), page.getPageSize());
        MappedStatement mappedStatement = dialectFactory.compile(Command.QUERY, queryStatement, methodInfo);
        page.setRows((Collection) session.execute(mappedStatement));
        return page;
    }

    @Override
    public <T> boolean exists(QueryWrapper<T> queryWrapper) {
        QueryStatement statement = queryWrapper.statement();
        SelectStatement selectStatement = statement.getSelectStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement();
        listColumnStatement.add(new SymbolStatement.NumberStatement("1"));
        selectStatement.setSelectList(listColumnStatement);
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(new SymbolStatement.NumberStatement("1"));
        limitStatement.setSecond(new SymbolStatement.NumberStatement("0"));
        statement.setLimitStatement(limitStatement);
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer.class);
        MappedStatement mappedStatement = dialectFactory.compile(Command.QUERY, queryWrapper.statement(), methodInfo);
        Integer result = (Integer) session.execute(mappedStatement);
        return result != null;
    }

    protected QueryStatement pageQueryStatement(QueryStatement queryStatement, long startRow, long pageNum) {
        LimitStatement limitStatement = queryStatement.getLimitStatement();
        if (limitStatement == null) {
            limitStatement = new LimitStatement();
            if (offset) {
                limitStatement.setOffset(true);
                limitStatement.setFirst(new TakeMarkInvokerStatement(null, pageNum));
                limitStatement.setSecond(new TakeMarkInvokerStatement(null, startRow));
            } else {
                limitStatement.setOffset(false);
                limitStatement.setFirst(new TakeMarkInvokerStatement(null, startRow));
                limitStatement.setSecond(new TakeMarkInvokerStatement(null, pageNum));
            }
            queryStatement.setLimitStatement(limitStatement);
        } else {
            throw new DreamRunTimeException("采用自动分页方式，不支持手动分页");
        }
        return queryStatement;
    }

    protected QueryStatement countQueryStatement(QueryStatement statement) {
        statement.setOrderStatement(null);
        FunctionStatement.CountStatement countStatement = new FunctionStatement.CountStatement();
        ListColumnStatement paramListColumnStatement = new ListColumnStatement(",");
        paramListColumnStatement.add(new SymbolStatement.LetterStatement("*"));
        countStatement.setParamsStatement(paramListColumnStatement);
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(countStatement);
        QueryStatement queryStatement = new QueryStatement();
        SelectStatement newSelectStatement = new SelectStatement();
        newSelectStatement.setSelectList(listColumnStatement);
        queryStatement.setSelectStatement(newSelectStatement);
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(new BraceStatement(statement));
        aliasStatement.setAlias(new SymbolStatement.SingleMarkStatement("t_tmp"));
        FromStatement fromStatement = new FromStatement();
        fromStatement.setMainTable(aliasStatement);
        queryStatement.setFromStatement(fromStatement);
        return queryStatement;
    }

    protected MethodInfo getMethodInfo(Class<? extends Collection> rowType, Class<?> colType) {
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setConfiguration(configuration);
        methodInfo.setRowType(rowType);
        methodInfo.setColType(colType);
        methodInfo.setResultSetHandler(resultSetHandler);
        if (consumer != null) {
            consumer.accept(methodInfo);
        }
        return methodInfo;
    }
}
