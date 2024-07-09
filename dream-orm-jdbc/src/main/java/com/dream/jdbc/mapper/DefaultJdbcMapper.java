package com.dream.jdbc.mapper;

import com.dream.antlr.config.ExprInfo;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.sql.ToSQL;
import com.dream.jdbc.core.JdbcBatchMappedStatement;
import com.dream.jdbc.core.JdbcResultSetHandler;
import com.dream.jdbc.core.JdbcStatementHandler;
import com.dream.jdbc.core.StatementSetter;
import com.dream.jdbc.row.RowMapping;
import com.dream.system.config.Command;
import com.dream.system.config.Compile;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
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
    private final ToSQL toSQL;

    public DefaultJdbcMapper(Session session, ToSQL toSQL) {
        this.session = session;
        this.tableFactory = session.getConfiguration().getTableFactory();
        this.typeHandlerFactory = session.getConfiguration().getTypeHandlerFactory();
        this.toSQL = toSQL;
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
        JdbcBatchMappedStatement jdbcBatchMappedStatement = new JdbcBatchMappedStatement(methodInfo, argList, Command.BATCH, sql, tableSet(sql));
        return (List<Object>) session.execute(jdbcBatchMappedStatement);
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
                    joiner.add(SystemUtil.key(column, toSQL));
                } else {
                    joiner.add(SystemUtil.key(column, toSQL) + " " + SystemUtil.key(name, toSQL));
                }
            }
        }
        if (sql != null) {
            return queryForList("select " + joiner + " from " + SystemUtil.key(tableName, toSQL) + " where " + sql, type, args);
        } else {
            return queryForList("select " + joiner + " from " + SystemUtil.key(tableName, toSQL), type, args);
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
                        joiner.add(SystemUtil.key(columnInfo.getColumn(), toSQL) + "=?");
                        valueList.add(value);
                    }
                }
            }
        }
        valueList.add(primValue);
        return execute("update " + SystemUtil.key(tableName, toSQL) + " set " + joiner + " where " + SystemUtil.key(primColumn.getColumn(), toSQL) + "=?", valueList.toArray(new Object[]{valueList.size()}));
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
                    columnList.add(SystemUtil.key(columnInfo.getColumn(), toSQL));
                    valueList.add(value);
                }
            }
        }
        if (columnList.size() > 0) {
            String[] marks = new String[columnList.size()];
            Arrays.fill(marks, "?");
            return execute("insert into " + SystemUtil.key(tableName, toSQL) + "(" + String.join(",", columnList) + ")values(" + String.join(",", marks) + ")", valueList.toArray(new Object[]{valueList.size()}));
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
                try {
                    typeHandlerList.add(typeHandlerFactory.getTypeHandler(field.getType(), columnInfo.getJdbcType()));
                } catch (TypeHandlerNotFoundException e) {
                    throw new DreamRunTimeException(e);
                }
            }
        }
        String[] marks = new String[columnList.size()];
        Arrays.fill(marks, "?");
        return batchExecute("insert into " + SystemUtil.key(tableName, toSQL) + "(" + columnList.stream().map(columnInfo -> SystemUtil.key(columnInfo.getColumn(), toSQL)).collect(Collectors.joining(",")) + ")values(" + String.join(",", marks) + ")", viewList, (ps, mappedStatement) -> {
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
                    try {
                        typeHandlerList.add(typeHandlerFactory.getTypeHandler(field.getType(), columnInfo.getJdbcType()));
                    } catch (TypeHandlerNotFoundException e) {
                        throw new DreamRunTimeException(e);
                    }
                }
            }
        }
        try {
            typeHandlerList.add(typeHandlerFactory.getTypeHandler(primColumn.getField().getType(), primColumn.getJdbcType()));
        } catch (TypeHandlerNotFoundException e) {
            throw new DreamRunTimeException(e);
        }
        return batchExecute("update " + SystemUtil.key(tableName, toSQL) + " set " + columnList.stream().map(columnInfo -> SystemUtil.key(columnInfo.getColumn(), toSQL) + "=?").collect(Collectors.joining(",")) + " where " + primColumn.getColumn() + "=?", viewList, (ps, mappedStatement) -> {
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
}
