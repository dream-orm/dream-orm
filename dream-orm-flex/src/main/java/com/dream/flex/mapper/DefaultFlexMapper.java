package com.dream.flex.mapper;

import com.dream.antlr.smt.*;
import com.dream.flex.config.FlexBatchMappedStatement;
import com.dream.flex.config.SqlInfo;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.def.InsertDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.dialect.FlexDialect;
import com.dream.flex.invoker.FlexMarkInvokerStatement;
import com.dream.system.config.*;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.resultsethandler.SimpleResultSetHandler;
import com.dream.system.core.session.Session;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.ObjectTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.tree.Tree;
import com.dream.util.tree.TreeUtil;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class DefaultFlexMapper implements FlexMapper {
    private Session session;
    private Configuration configuration;
    private TypeHandlerFactory typeHandlerFactory;
    private boolean offset = false;
    private FlexDialect flexDialect;
    private ResultSetHandler resultSetHandler;
    private Consumer<MethodInfo> consumer;

    public DefaultFlexMapper(Session session, FlexDialect flexDialect) {
        this(session, flexDialect, new SimpleResultSetHandler());
    }

    public DefaultFlexMapper(Session session, FlexDialect flexDialect, ResultSetHandler resultSetHandler) {
        this.session = session;
        this.flexDialect = flexDialect;
        this.resultSetHandler = resultSetHandler;
        this.configuration = session.getConfiguration();
        typeHandlerFactory = configuration.getTypeHandlerFactory();
    }

    @Override
    public FlexMapper useDialect(FlexDialect flexDialect) {
        return new DefaultFlexMapper(session, flexDialect, resultSetHandler);
    }

    @Override
    public FlexMapper useMethodInfo(Consumer<MethodInfo> consumer) {
        DefaultFlexMapper flexMapper = new DefaultFlexMapper(session, flexDialect, resultSetHandler);
        flexMapper.consumer = consumer;
        return flexMapper;
    }

    @Override
    public <T> T selectOne(QueryDef queryDef, Class<T> type) {
        return selectOne(queryDef.statement(), type);
    }

    public <T> T selectOne(QueryStatement statement, Class<T> type) {
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, type);
        SqlInfo sqlInfo = flexDialect.toSQL(statement, methodInfo);
        MappedStatement mappedStatement = getMappedStatement(sqlInfo, Command.QUERY, methodInfo);
        return (T) session.execute(mappedStatement);
    }

    @Override
    public <T> List<T> selectList(QueryDef queryDef, Class<T> type) {
        MethodInfo methodInfo = getMethodInfo(List.class, type);
        SqlInfo sqlInfo = flexDialect.toSQL(queryDef.statement(), methodInfo);
        MappedStatement mappedStatement = getMappedStatement(sqlInfo, Command.QUERY, methodInfo);
        return (List<T>) session.execute(mappedStatement);
    }

    @Override
    public <T extends Tree> List<T> selectTree(QueryDef queryDef, Class<T> type) {
        MethodInfo methodInfo = getMethodInfo(List.class, type);
        SqlInfo sqlInfo = flexDialect.toSQL(queryDef.statement(), methodInfo);
        methodInfo.addDestroyAction((result, mappedStatement, session) -> TreeUtil.toTree((Collection<? extends Tree>) result));
        MappedStatement mappedStatement = getMappedStatement(sqlInfo, Command.QUERY, methodInfo);
        return (List<T>) session.execute(mappedStatement);
    }

    @Override
    public <T> Page<T> selectPage(QueryDef queryDef, Class<T> type, Page page) {
        QueryStatement statement = queryDef.statement();
        if (page.getTotal() == 0) {
            MethodInfo countMethodInfo = getMethodInfo(NonCollection.class, Long.class);
            MappedStatement countMappedStatement = getMappedStatement(flexDialect.toSQL(countQueryStatement(statement.clone()), countMethodInfo), Command.QUERY, countMethodInfo);
            page.setTotal((long) session.execute(countMappedStatement));
        }
        MethodInfo methodInfo = getMethodInfo(Collection.class, type);
        QueryStatement queryStatement = pageQueryStatement(statement, page.getStartRow(), page.getPageSize());
        MappedStatement mappedStatement = getMappedStatement(flexDialect.toSQL(queryStatement, methodInfo), Command.QUERY, methodInfo);
        page.setRows((Collection) session.execute(mappedStatement));
        return page;
    }

    @Override
    public int update(UpdateDef updateDef) {
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer.class);
        SqlInfo sqlInfo = flexDialect.toSQL(updateDef.statement(), methodInfo);
        MappedStatement mappedStatement = getMappedStatement(sqlInfo, Command.UPDATE, methodInfo);
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
            SqlInfo sqlInfo = flexDialect.toSQL(updateDef.statement(), methodInfo);
            MappedStatement mappedStatement = getMappedStatement(sqlInfo, Command.UPDATE, methodInfo);
            mappedStatementList.add(mappedStatement);
        }
        FlexBatchMappedStatement batchMappedStatement = new FlexBatchMappedStatement(methodInfo, mappedStatementList);
        batchMappedStatement.setBatchSize(batchSize);
        return (List<int[]>) session.execute(batchMappedStatement);
    }

    @Override
    public int delete(DeleteDef deleteDef) {
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer.class);
        SqlInfo sqlInfo = flexDialect.toSQL(deleteDef.statement(), methodInfo);
        MappedStatement mappedStatement = getMappedStatement(sqlInfo, Command.DELETE, methodInfo);
        return (int) session.execute(mappedStatement);
    }

    @Override
    public int insert(InsertDef insertDef) {
        MethodInfo methodInfo = getMethodInfo(NonCollection.class, Integer.class);
        SqlInfo sqlInfo = flexDialect.toSQL(insertDef.statement(), methodInfo);
        MappedStatement mappedStatement = getMappedStatement(sqlInfo, Command.INSERT, methodInfo);
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
            SqlInfo sqlInfo = flexDialect.toSQL(insertDef.statement(), methodInfo);
            MappedStatement mappedStatement = getMappedStatement(sqlInfo, Command.INSERT, methodInfo);
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
        limitStatement.setFirst(new SymbolStatement.NumberStatement("0"));
        limitStatement.setSecond(new SymbolStatement.NumberStatement("1"));
        statement.setLimitStatement(limitStatement);
        Integer result = selectOne(statement, Integer.class);
        return result != null;
    }

    protected QueryStatement pageQueryStatement(QueryStatement queryStatement, long startRow, long pageNum) {
        LimitStatement limitStatement = queryStatement.getLimitStatement();
        if (limitStatement == null) {
            limitStatement = new LimitStatement();
            if (offset) {
                limitStatement.setOffset(true);
                limitStatement.setFirst(new FlexMarkInvokerStatement(pageNum));
                limitStatement.setSecond(new FlexMarkInvokerStatement(startRow));
            } else {
                limitStatement.setOffset(false);
                limitStatement.setFirst(new FlexMarkInvokerStatement(startRow));
                limitStatement.setSecond(new FlexMarkInvokerStatement(pageNum));
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

    protected MappedStatement getMappedStatement(SqlInfo sqlInfo, Command command, MethodInfo methodInfo) {
        List<Object> paramList = sqlInfo.getParamList();
        List<MappedParam> mappedParamList = null;
        if (!ObjectUtil.isNull(paramList)) {
            mappedParamList = new ArrayList<>(paramList.size());
            for (Object param : paramList) {
                TypeHandler typeHandler;
                if (param == null) {
                    typeHandler = new ObjectTypeHandler();
                } else {
                    try {
                        typeHandler = typeHandlerFactory.getTypeHandler(param.getClass(), Types.NULL);
                    } catch (TypeHandlerNotFoundException e) {
                        throw new DreamRunTimeException(e);
                    }
                }
                mappedParamList.add(new MappedParam().setParamValue(param).setTypeHandler(typeHandler));
            }
        }
        MappedSql mappedSql = new MappedSql(command, sqlInfo.getSql(), sqlInfo.getTableSet());
        MappedStatement mappedStatement = new MappedStatement
                .Builder()
                .methodInfo(methodInfo)
                .mappedParamList(mappedParamList)
                .mappedSql(mappedSql)
                .build();
        return mappedStatement;
    }
}
