package com.dream.flex.mapper;

import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToSQL;
import com.dream.flex.config.FlexBatchMappedStatement;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.def.UpdateDef;
import com.dream.struct.factory.DefaultStructFactory;
import com.dream.struct.factory.StructFactory;
import com.dream.struct.invoker.TakeMarkInvokerStatement;
import com.dream.system.config.*;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.resultsethandler.SimpleResultSetHandler;
import com.dream.system.core.session.Session;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.tree.Tree;
import com.dream.util.tree.TreeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class DefaultFlexMapper implements FlexMapper {
    private Session session;
    private Configuration configuration;
    private boolean offset = true;
    private StructFactory dialectFactory;
    private ResultSetHandler resultSetHandler;
    private Consumer<MethodInfo> consumer;

    public DefaultFlexMapper(Session session, ToSQL toSQL) {
        this(session, new DefaultStructFactory(toSQL));
    }

    public DefaultFlexMapper(Session session, StructFactory dialectFactory) {
        this(session, dialectFactory, new SimpleResultSetHandler());
    }

    public DefaultFlexMapper(Session session, StructFactory dialectFactory, ResultSetHandler resultSetHandler) {
        this.session = session;
        this.dialectFactory = dialectFactory;
        this.resultSetHandler = resultSetHandler;
        this.configuration = session.getConfiguration();
    }

    @Override
    public FlexMapper useDialect(StructFactory dialectFactory) {
        return new DefaultFlexMapper(session, dialectFactory, resultSetHandler);
    }

    @Override
    public FlexMapper useMethodInfo(Consumer<MethodInfo> consumer) {
        DefaultFlexMapper flexMapper = new DefaultFlexMapper(session, dialectFactory, resultSetHandler);
        flexMapper.consumer = consumer;
        return flexMapper;
    }

    @Override
    public <T> T selectOne(QueryDef queryDef, Class<T> type) {
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, type);
        MappedStatement mappedStatement = dialectFactory.compile(Command.QUERY, queryDef.statement(), methodInfo);
        return (T) session.execute(mappedStatement);
    }

    @Override
    public <T> List<T> selectList(QueryDef queryDef, Class<T> type) {
        MethodInfo methodInfo = getMethodInfo(List.class, type);
        MappedStatement mappedStatement = dialectFactory.compile(Command.QUERY, queryDef.statement(), methodInfo);
        return (List<T>) session.execute(mappedStatement);
    }

    @Override
    public <T extends Tree> List<T> selectTree(QueryDef queryDef, Class<T> type) {
        MethodInfo methodInfo = getMethodInfo(List.class, type);
        methodInfo.addDestroyAction((result, mappedStatement, session) -> TreeUtil.toTree((Collection<? extends Tree>) result));
        MappedStatement mappedStatement = dialectFactory.compile(Command.QUERY, queryDef.statement(), methodInfo);
        return (List<T>) session.execute(mappedStatement);
    }

    @Override
    public <T> Page<T> selectPage(QueryDef queryDef, Class<T> type, Page page) {
        QueryStatement statement = queryDef.statement();
        if (page.getTotal() == 0) {
            MethodInfo countMethodInfo = getMethodInfo(NonCollection.class, Long.class);
            MappedStatement countMappedStatement = dialectFactory.compile(Command.QUERY, countQueryStatement(queryDef.statement().clone()), countMethodInfo);
            page.setTotal((long) session.execute(countMappedStatement));
        }
        MethodInfo methodInfo = getMethodInfo(Collection.class, type);
        QueryStatement queryStatement = pageQueryStatement(statement, page.getStartRow(), page.getPageSize());
        MappedStatement mappedStatement = dialectFactory.compile(Command.QUERY, queryStatement, methodInfo);
        page.setRows((Collection) session.execute(mappedStatement));
        return page;
    }

    @Override
    public int update(UpdateDef updateDef) {
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer.class);
        MappedStatement mappedStatement = dialectFactory.compile(Command.UPDATE, updateDef.statement(), methodInfo);
        return (int) session.execute(mappedStatement);
    }

    @Override
    public List<int[]> batchUpdate(List<UpdateDef> updateDefList, int batchSize) {
        if (ObjectUtil.isNull(updateDefList)) {
            return null;
        }
        List<MappedStatement> mappedStatementList = new ArrayList<>(updateDefList.size());
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer[].class);
        for (UpdateDef updateDef : updateDefList) {
            MappedStatement mappedStatement = dialectFactory.compile(Command.UPDATE, updateDef.statement(), methodInfo);
            mappedStatementList.add(mappedStatement);
        }
        FlexBatchMappedStatement batchMappedStatement = new FlexBatchMappedStatement(methodInfo, mappedStatementList);
        batchMappedStatement.setBatchSize(batchSize);
        return (List<int[]>) session.execute(batchMappedStatement);
    }

    @Override
    public int delete(DeleteDef deleteDef) {
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer.class);
        MappedStatement mappedStatement = dialectFactory.compile(Command.UPDATE, deleteDef.statement(), methodInfo);
        return (int) session.execute(mappedStatement);
    }

    @Override
    public int insert(InsertDef insertDef) {
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer.class);
        MappedStatement mappedStatement = dialectFactory.compile(Command.INSERT, insertDef.statement(), methodInfo);
        return (int) session.execute(mappedStatement);
    }

    @Override
    public List<int[]> batchInsert(List<InsertDef> insertDefList, int batchSize) {
        if (ObjectUtil.isNull(insertDefList)) {
            return null;
        }
        List<MappedStatement> mappedStatementList = new ArrayList<>(insertDefList.size());
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer[].class);
        for (InsertDef insertDef : insertDefList) {
            MappedStatement mappedStatement = dialectFactory.compile(Command.INSERT, insertDef.statement(), methodInfo);
            mappedStatementList.add(mappedStatement);
        }
        FlexBatchMappedStatement batchMappedStatement = new FlexBatchMappedStatement(methodInfo, mappedStatementList);
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
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer.class);
        MappedStatement mappedStatement = dialectFactory.compile(Command.QUERY, queryDef.statement(), methodInfo);
        Integer result = (Integer) session.execute(mappedStatement);
        return result != null;
    }

    protected QueryStatement pageQueryStatement(QueryStatement queryStatement, long startRow, long pageNum) {
        LimitStatement limitStatement = queryStatement.getLimitStatement();
        if (limitStatement == null) {
            limitStatement = new LimitStatement();
            if (offset) {
                limitStatement.setOffset(true);
                limitStatement.setFirst(new TakeMarkInvokerStatement(pageNum));
                limitStatement.setSecond(new TakeMarkInvokerStatement(startRow));
            } else {
                limitStatement.setOffset(false);
                limitStatement.setFirst(new TakeMarkInvokerStatement(startRow));
                limitStatement.setSecond(new TakeMarkInvokerStatement(pageNum));
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
