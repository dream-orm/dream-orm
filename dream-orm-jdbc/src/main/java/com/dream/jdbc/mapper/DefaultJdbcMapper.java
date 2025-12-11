package com.dream.jdbc.mapper;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.read.ExprReader;
import com.dream.jdbc.core.JdbcResultSetHandler;
import com.dream.jdbc.core.JdbcStatementHandler;
import com.dream.jdbc.core.StatementSetter;
import com.dream.jdbc.row.RowMapping;
import com.dream.system.config.*;
import com.dream.system.core.session.Session;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.LowHashSet;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


public class DefaultJdbcMapper implements JdbcMapper {
    private final Session session;
    private final TableFactory tableFactory;
    private final TypeHandlerFactory typeHandlerFactory;

    public DefaultJdbcMapper(Session session) {
        this.session = session;
        Configuration configuration = session.getConfiguration();
        this.tableFactory = configuration.getTableFactory();
        this.typeHandlerFactory = configuration.getTypeHandlerFactory();
    }

    @Override
    public int execute(String sql, StatementSetter statementSetter) {
        JdbcStatementHandler jdbcStatementHandler = new JdbcStatementHandler(statementSetter);
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setStatementHandler(jdbcStatementHandler);
        methodInfo.setCompile(Compile.ANTLR_COMPILED);
        methodInfo.setConfiguration(session.getConfiguration());
        MappedStatement mappedStatement = new MappedStatement.Builder()
                .methodInfo(methodInfo)
                .command(Command.UPDATE)
                .sql(sql)
                .tableSet(tableSet(sql))
                .build();
        return (int) session.execute(mappedStatement);
    }

    @Override
    public <T> List<Object> batchExecute(String sql, List<T> argList, StatementSetter statementSetter, int batchSize) {
        if (ObjectUtil.isNull(argList)) {
            return null;
        }
        JdbcStatementHandler jdbcStatementHandler = new JdbcStatementHandler(statementSetter);
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setStatementHandler(jdbcStatementHandler);
        methodInfo.setCompile(Compile.ANTLR_COMPILED);
        methodInfo.setConfiguration(session.getConfiguration());
        List<MappedStatement> mappedStatementList = compile(methodInfo, argList, Command.BATCH, sql, tableSet(sql));
        BatchMappedStatement batchMappedStatement = new BatchMappedStatement(mappedStatementList);
        return (List<Object>) session.execute(batchMappedStatement);
    }

    @Override
    public <T> List<T> queryForList(String sql, StatementSetter statementSetter, RowMapping<T> rowMapping) {
        JdbcStatementHandler jdbcStatementHandler = new JdbcStatementHandler(statementSetter);
        JdbcResultSetHandler jdbcResultSetHandler = new JdbcResultSetHandler(rowMapping);
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setStatementHandler(jdbcStatementHandler);
        methodInfo.setResultSetHandler(jdbcResultSetHandler);
        methodInfo.setCompile(Compile.ANTLR_COMPILED);
        methodInfo.setConfiguration(session.getConfiguration());
        MappedStatement mappedStatement = new MappedStatement.Builder()
                .methodInfo(methodInfo)
                .command(Command.QUERY)
                .sql(sql).tableSet(tableSet(sql)).build();
        return (List<T>) session.execute(mappedStatement);
    }

    @Override
    public <T> List<T> selectList(Class<T> type, String sql, Object... args) {
        String tableName = SystemUtil.getTableName(type);
        TableInfo tableInfo = tableFactory.getTableInfo(tableName);
        if (tableInfo == null) {
            throw new DreamRunTimeException("表'" + tableName + "'未在TableFactory注册");
        }
        List<Field> fieldList = ReflectUtil.findField(type);
        StringJoiner joiner = new StringJoiner(",");
        for (Field field : fieldList) {
            String fieldName = field.getName();
            ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
            if (columnInfo != null) {
                String column = columnInfo.getColumn();
                String name = columnInfo.getName();
                if (name.equals(column)) {
                    joiner.add(column);
                } else {
                    joiner.add(column + " " + name);
                }
            }
        }
        if (sql != null) {
            return queryForList("select " + joiner + " from " + tableName + " where " + sql, type, args);
        } else {
            return queryForList("select " + joiner + " from " + tableName, type, args);
        }
    }

    @Override
    public int updateById(Object view) {
        return updateById(view, false);
    }

    @Override
    public int updateNonById(Object view) {
        return updateById(view, true);
    }

    protected int updateById(Object view, boolean filterNull) {
        Class<?> type = view.getClass();
        String tableName = SystemUtil.getTableName(type);
        if (ObjectUtil.isNull(tableName)) {
            throw new DreamRunTimeException(type.getName() + "未绑定表");
        }
        TableInfo tableInfo = tableFactory.getTableInfo(tableName);
        if (tableInfo == null) {
            throw new DreamRunTimeException("表'" + tableName + "'未在TableFactory注册");
        }
        List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
        if (primKeys == null || primKeys.isEmpty()) {
            throw new DreamRunTimeException("表'" + tableInfo.getTable() + "'未注册主键");
        }
        if (primKeys.size() > 1) {
            throw new DreamRunTimeException("表'" + tableInfo.getTable() + "'存在多个主键");
        }
        ColumnInfo primColumn = primKeys.get(0);
        ObjectWrapper wrapper = ObjectWrapper.wrapper(view);
        List<Field> fieldList = ReflectUtil.findField(type);
        StringJoiner joiner = new StringJoiner(",");
        List<Object> valueList = new ArrayList<>();
        Object primValue = null;
        for (Field field : fieldList) {
            String fieldName = field.getName();
            ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
            if (columnInfo != null) {
                Object value = wrapper.get(fieldName);
                if (fieldName.equals(primColumn.getName())) {
                    primValue = value;
                } else {
                    if (value != null || !filterNull) {
                        joiner.add(columnInfo.getColumn() + "=?");
                        valueList.add(value);
                    }
                }
            }
        }
        valueList.add(primValue);
        return execute("update " + tableName + " set " + joiner + " where " + primColumn.getColumn() + "=?", valueList.toArray(new Object[]{valueList.size()}));
    }

    @Override
    public int insert(Object view) {
        Class<?> type = view.getClass();
        String tableName = SystemUtil.getTableName(type);
        if (ObjectUtil.isNull(tableName)) {
            throw new DreamRunTimeException(type.getName() + "未绑定表");
        }
        TableInfo tableInfo = tableFactory.getTableInfo(tableName);
        if (tableInfo == null) {
            throw new DreamRunTimeException("表'" + tableName + "'未在TableFactory注册");
        }
        ObjectWrapper wrapper = ObjectWrapper.wrapper(view);
        List<Field> fieldList = ReflectUtil.findField(type);
        List<String> columnList = new ArrayList<>();
        List<Object> valueList = new ArrayList<>();
        for (Field field : fieldList) {
            String fieldName = field.getName();
            ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
            if (columnInfo != null) {
                Object value = wrapper.get(fieldName);
                if (value != null) {
                    columnList.add(columnInfo.getColumn());
                    valueList.add(value);
                }
            }
        }
        if (columnList.size() > 0) {
            String[] marks = new String[columnList.size()];
            Arrays.fill(marks, "?");
            return execute("insert into " + tableName + "(" + String.join(",", columnList) + ")values(" + String.join(",", marks) + ")", valueList.toArray(new Object[]{valueList.size()}));
        } else {
            return 0;
        }
    }

    @Override
    public <T> List<Object> batchInsert(List<T> viewList) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        Class<?> type = viewList.get(0).getClass();
        String tableName = SystemUtil.getTableName(type);
        if (ObjectUtil.isNull(tableName)) {
            throw new DreamRunTimeException(type.getName() + "未绑定表");
        }
        TableInfo tableInfo = tableFactory.getTableInfo(tableName);
        if (tableInfo == null) {
            throw new DreamRunTimeException("表'" + tableName + "'未在TableFactory注册");
        }
        List<Field> fieldList = ReflectUtil.findField(type);
        List<ColumnInfo> columnList = new ArrayList<>();
        List<TypeHandler> typeHandlerList = new ArrayList<>();
        for (Field field : fieldList) {
            String fieldName = field.getName();
            ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
            if (columnInfo != null) {
                columnList.add(columnInfo);
                TypeHandler typeHandler = columnInfo.getTypeHandler();
                try {
                    if (typeHandler == null) {
                        typeHandler = typeHandlerFactory.getTypeHandler(field.getType(), columnInfo.getJdbcType());
                    }
                } catch (TypeHandlerNotFoundException e) {
                    throw new DreamRunTimeException(e);
                }
                typeHandlerList.add(typeHandler);
            }
        }
        String[] marks = new String[columnList.size()];
        Arrays.fill(marks, "?");
        return batchExecute("insert into " + tableName + "(" + columnList.stream().map(ColumnInfo::getColumn).collect(Collectors.joining(",")) + ")values(" + String.join(",", marks) + ")", viewList, (ps, mappedStatement) -> {
            Object arg = mappedStatement.getArg();
            ObjectWrapper wrapper = ObjectWrapper.wrapper(arg);
            for (int i = 0; i < columnList.size(); i++) {
                ColumnInfo columnInfo = columnList.get(i);
                Object value = wrapper.get(columnInfo.getName());
                typeHandlerList.get(i).setParam(ps, i + 1, value, columnInfo.getJdbcType());
            }
        });
    }

    public <T> List<Object> batchUpdateById(List<T> viewList) {
        if (viewList == null || viewList.isEmpty()) {
            return null;
        }
        Class<?> type = viewList.get(0).getClass();
        String tableName = SystemUtil.getTableName(type);
        if (ObjectUtil.isNull(tableName)) {
            throw new DreamRunTimeException(type.getName() + "未绑定表");
        }
        TableInfo tableInfo = tableFactory.getTableInfo(tableName);
        if (tableInfo == null) {
            throw new DreamRunTimeException("表'" + tableName + "'未在TableFactory注册");
        }
        List<Field> fieldList = ReflectUtil.findField(type);
        List<ColumnInfo> columnList = new ArrayList<>();
        List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
        if (primKeys == null || primKeys.isEmpty()) {
            throw new DreamRunTimeException("表'" + tableInfo.getTable() + "'未注册主键");
        }
        if (primKeys.size() > 1) {
            throw new DreamRunTimeException("表'" + tableInfo.getTable() + "'存在多个主键");
        }
        ColumnInfo primColumn = primKeys.get(0);
        List<TypeHandler> typeHandlerList = new ArrayList<>();
        for (Field field : fieldList) {
            String fieldName = field.getName();
            ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
            if (columnInfo != null) {
                if (!fieldName.equals(primColumn.getName())) {
                    columnList.add(columnInfo);
                    TypeHandler typeHandler = columnInfo.getTypeHandler();
                    try {
                        if (typeHandler == null) {
                            typeHandler = typeHandlerFactory.getTypeHandler(field.getType(), columnInfo.getJdbcType());
                        }
                    } catch (TypeHandlerNotFoundException e) {
                        throw new DreamRunTimeException(e);
                    }
                    typeHandlerList.add(typeHandler);
                }
            }
        }
        TypeHandler typeHandler = primColumn.getTypeHandler();
        try {
            if (typeHandler == null) {
                typeHandler = typeHandlerFactory.getTypeHandler(primColumn.getField().getType(), primColumn.getJdbcType());
            }
        } catch (TypeHandlerNotFoundException e) {
            throw new DreamRunTimeException(e);
        }
        typeHandlerList.add(typeHandler);
        return batchExecute("update " + tableName + " set " + columnList.stream().map(columnInfo -> columnInfo.getColumn() + "=?").collect(Collectors.joining(",")) + " where " + primColumn.getColumn() + "=?", viewList, (ps, mappedStatement) -> {
            Object arg = mappedStatement.getArg();
            ObjectWrapper wrapper = ObjectWrapper.wrapper(arg);
            int size = columnList.size();
            for (int i = 0; i < size; i++) {
                ColumnInfo columnInfo = columnList.get(i);
                Object value = wrapper.get(columnInfo.getName());
                typeHandlerList.get(i).setParam(ps, i + 1, value, columnInfo.getJdbcType());
            }
            typeHandlerList.get(size).setParam(ps, size + 1, wrapper.get(primColumn.getName()), primColumn.getJdbcType());
        });
    }

    protected Set<String> tableSet(String sql) {
        ExprReader exprReader = new ExprReader(sql);
        ExprInfo exprInfo = exprReader.push();
        switch (exprInfo.getExprType()) {
            case UPDATE:
            case DELETE:
                exprInfo = exprReader.push();
                break;
            case INSERT:
                exprReader.push();
                exprInfo = exprReader.push();
                break;
            default:
                exprInfo = null;
        }
        if (exprInfo != null) {
            LowHashSet tableSet = new LowHashSet();
            tableSet.add(exprInfo.getInfo());
            return tableSet;
        }
        return null;
    }

    protected List<MappedStatement> compile(MethodInfo methodInfo, List<?> argList, Command command, String sql, Set<String> tableSet) {
        List<MappedStatement> mappedStatementList = new ArrayList<>(argList.size());
        for (Object arg : argList) {
            MappedStatement mappedStatement = new MappedStatement.Builder().methodInfo(methodInfo).command(command).sql(sql).tableSet(tableSet).arg(arg).build();
            mappedStatementList.add(mappedStatement);
        }
        return mappedStatementList;
    }
}
