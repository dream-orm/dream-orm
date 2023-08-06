package com.moxa.dream.flex.mapper;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.config.SqlInfo;
import com.moxa.dream.flex.def.Delete;
import com.moxa.dream.flex.def.Insert;
import com.moxa.dream.flex.def.Query;
import com.moxa.dream.flex.def.Update;
import com.moxa.dream.flex.inject.FlexInject;
import com.moxa.dream.flex.invoker.FlexMarkInvoker;
import com.moxa.dream.flex.invoker.FlexMarkInvokerStatement;
import com.moxa.dream.flex.invoker.FlexTableInvoker;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.*;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.inject.PageInject;
import com.moxa.dream.system.inject.factory.InjectFactory;
import com.moxa.dream.system.typehandler.TypeHandlerNotFoundException;
import com.moxa.dream.system.typehandler.factory.TypeHandlerFactory;
import com.moxa.dream.system.typehandler.handler.ObjectTypeHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.util.SystemUtil;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.sql.Types;
import java.util.*;

public class DefaultFlexMapper implements FlexMapper {
    private Session session;
    private Configuration configuration;
    private ToSQL toSQL;
    private TypeHandlerFactory typeHandlerFactory;
    private InjectFactory injectFactory;
    private boolean offset;

    public DefaultFlexMapper(Session session, ToSQL toSQL) {
        this.session = session;
        this.configuration = session.getConfiguration();
        this.toSQL = toSQL;
        typeHandlerFactory = configuration.getTypeHandlerFactory();
        injectFactory = configuration.getInjectFactory();
        PageInject pageInject = injectFactory.getInject(PageInject.class);
        if (pageInject != null) {
            this.offset = pageInject.isOffset();
        }
        FlexInject flexInject = injectFactory.getInject(FlexInject.class);
        if (flexInject == null) {
            injectFactory.injects(new FlexInject());
        }
        WHITE_SET.add(FlexInject.class);
    }

    @Override
    public <T> T selectOne(Query query, Class<T> type) {
        MappedStatement mappedStatement = getMappedStatement(Command.QUERY, query.statement(), NonCollection.class, type);
        return (T) session.execute(mappedStatement);
    }

    @Override
    public <T> List<T> selectList(Query query, Class<T> type) {
        MappedStatement mappedStatement = getMappedStatement(Command.QUERY, query.statement(), List.class, type);
        return (List<T>) session.execute(mappedStatement);
    }

    @Override
    public <T> Page<T> selectPage(Query query, Class<T> type, Page page) {
        QueryStatement statement = query.statement();
        QueryStatement queryStatement = pageQueryStatement(statement, page.getStartRow(), page.getPageSize());
        MappedStatement mappedStatement = getMappedStatement(Command.QUERY, queryStatement, Collection.class, type);
        MappedStatement countMappedStatement = getMappedStatement(Command.QUERY, countQueryStatement(queryStatement), NonCollection.class, Long.class);
        page.setTotal((long) session.execute(countMappedStatement));
        page.setRows((Collection) session.execute(mappedStatement));
        return page;
    }

    @Override
    public int update(Update update) {
        MappedStatement mappedStatement = getMappedStatement(Command.UPDATE, update.getStatement(), NonCollection.class, Integer.class);
        return (int) session.execute(mappedStatement);
    }

    @Override
    public int delete(Delete delete) {
        MappedStatement mappedStatement = getMappedStatement(Command.DELETE, delete.getStatement(), NonCollection.class, Integer.class);
        return (int) session.execute(mappedStatement);
    }

    @Override
    public int insert(Insert insert) {
        MappedStatement mappedStatement = getMappedStatement(Command.INSERT, insert.getStatement(), NonCollection.class, Integer.class);
        return (int) session.execute(mappedStatement);
    }

    private QueryStatement pageQueryStatement(QueryStatement queryStatement, long startRow, long pageNum) {
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

    private QueryStatement countQueryStatement(QueryStatement statement) {
        QueryStatement queryStatement = new QueryStatement();
        SelectStatement selectStatement = statement.getSelectStatement();
        queryStatement.setSelectStatement(selectStatement);
        SelectStatement countSelectStatement = new SelectStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement();
        listColumnStatement.add(countFunctionStatement());
        countSelectStatement.setSelectList(listColumnStatement);
        queryStatement.setSelectStatement(countSelectStatement);
        if (!(selectStatement.isDistinct())) {
            UnionStatement unionStatement = queryStatement.getUnionStatement();
            if (unionStatement == null) {
                queryStatement.setFromStatement(statement.getFromStatement());
                queryStatement.setWhereStatement(statement.getWhereStatement());
                queryStatement.setGroupStatement(statement.getGroupStatement());
                queryStatement.setHavingStatement(statement.getHavingStatement());
                queryStatement.setUnionStatement(statement.getUnionStatement());
                queryStatement.setForUpdateStatement(statement.getForUpdateStatement());
                return queryStatement;
            }
        }
        AliasStatement aliasStatement = new AliasStatement();
        aliasStatement.setColumn(statement);
        aliasStatement.setAlias(new SymbolStatement.SingleMarkStatement("t_tmp"));
        FromStatement fromStatement = new FromStatement();
        fromStatement.setMainTable(aliasStatement);
        queryStatement.setFromStatement(fromStatement);
        return queryStatement;
    }

    private FunctionStatement countFunctionStatement() {
        FunctionStatement.CountStatement countStatement = new FunctionStatement.CountStatement();
        ListColumnStatement paramListColumnStatement = new ListColumnStatement(",");
        paramListColumnStatement.add(new SymbolStatement.LetterStatement("1"));
        countStatement.setParamsStatement(paramListColumnStatement);
        return countStatement;
    }

    private MethodInfo getMethodInfo(Statement statement, Class<? extends Collection> rowType, Class<?> colType) {
        PackageStatement packageStatement = new PackageStatement();
        packageStatement.setStatement(statement);
        MethodInfo methodInfo = new MethodInfo()
                .setConfiguration(configuration)
                .setStatement(packageStatement)
                .setRowType(rowType)
                .setColType(colType)
                .setCompile(Compile.ANTLR_COMPILED);
        injectFactory.inject(methodInfo, inject -> WHITE_SET.contains(inject.getClass()));
        return methodInfo;
    }

    private MappedStatement getMappedStatement(Command command, Statement statement, Class<? extends Collection> rowType, Class<?> colType) {
        MethodInfo methodInfo = getMethodInfo(statement, rowType, colType);
        SqlInfo sqlInfo = toSQL(methodInfo);
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
        String sql = sqlInfo.getSql();
        CacheKey methodKey = SystemUtil.cacheKey(sql, 5, false);
        methodKey.update(rowType, colType);
        methodInfo.setMethodKey(methodKey);
        CacheKey uniqueKey = methodKey.clone();
        uniqueKey.update(sqlInfo.getParamList().toArray());
        MappedSql mappedSql = new MappedSql(command.name(), sql, sqlInfo.getTableSet());
        MappedStatement mappedStatement = new MappedStatement
                .Builder()
                .mappedParamList(mappedParamList)
                .mappedSql(mappedSql)
                .uniqueKey(uniqueKey)
                .methodInfo(methodInfo)
                .build();
        return mappedStatement;
    }

    private SqlInfo toSQL(MethodInfo methodInfo) {
        Map<Class, Object> customMap = new HashMap<>();
        customMap.put(MethodInfo.class, methodInfo);
        customMap.put(Configuration.class, configuration);
        Assist assist = new Assist(configuration.getInvokerFactory(), customMap);
        String sql;
        try {
            sql = toSQL.toStr(methodInfo.getStatement(), assist, null);
        } catch (AntlrException e) {
            throw new DreamRunTimeException(e);
        }
        FlexMarkInvoker flexMarkInvoker = (FlexMarkInvoker) assist.getInvoker(FlexMarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        FlexTableInvoker flexTableInvoker = (FlexTableInvoker) assist.getInvoker(FlexTableInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE);
        List<Object> paramList = flexMarkInvoker.getParamList();
        Set<String> tableSet = flexTableInvoker.getTableSet();
        return new SqlInfo(sql, paramList, tableSet);
    }
}
