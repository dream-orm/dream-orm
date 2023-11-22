package com.dream.template.mapper;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.util.AntlrUtil;
import com.dream.system.antlr.invoker.MarkInvoker;
import com.dream.system.config.Command;
import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.action.DestroyAction;
import com.dream.system.core.action.InitAction;
import com.dream.system.core.session.Session;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.factory.TypeHandlerFactory;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.template.annotation.WrapType;
import com.dream.template.sequence.Sequence;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class InsertMapper extends WrapMapper {
    private final int CODE = 1;
    protected Sequence sequence;

    public InsertMapper(Session session, Sequence sequence) {
        super(session);
        this.sequence = sequence;
    }

    @Override
    protected MethodInfo getWrapMethodInfo(Configuration configuration, TableInfo tableInfo, List<Field> fieldList, Object arg) {
        String table = tableInfo.getTable();
        List<String> columnList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
        if (primKeys != null && !primKeys.isEmpty()) {
            for (ColumnInfo prim : primKeys) {
                String invokerSQL = AntlrUtil.invokerSQL(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, DREAM_TEMPLATE_PARAM + "." + prim.getName());
                columnList.add(prim.getColumn());
                valueList.add(invokerSQL);
            }
        }
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                String name = field.getName();
                ColumnInfo columnInfo = tableInfo.getColumnInfo(name);
                if (columnInfo != null) {
                    String column = columnInfo.getColumn();
                    String invokerSQL = AntlrUtil.invokerSQL(MarkInvoker.FUNCTION, Invoker.DEFAULT_NAMESPACE, DREAM_TEMPLATE_PARAM + "." + columnInfo.getName());
                    if (!columnInfo.isPrimary()) {
                        columnList.add(column);
                        valueList.add(invokerSQL);
                    }
                }
            }
        }
        String sql = "insert into " + table + "(" + String.join(",", columnList) + ")values(" + String.join(",", valueList) + ")";
        return getMethodInfo(configuration, tableInfo, sql);
    }

    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, String sql) {
        String[] columnNames = sequence.columnNames(tableInfo);
        TypeHandler[] typeHandlers = null;
        SequenceAction sequenceAction = new SequenceAction(tableInfo, sequence);
        if (columnNames != null && columnNames.length > 0) {
            typeHandlers = new TypeHandler[columnNames.length];
            TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
            for (int i = 0; i < columnNames.length; i++) {
                String column = columnNames[i];
                ColumnInfo columnInfo = tableInfo.getColumnInfo(column);
                if (columnInfo == null) {
                    throw new DreamRunTimeException("表" + tableInfo.getTable() + "不存在字段" + column);
                }
                Class<?> type = columnInfo.getField().getType();
                TypeHandler typeHandler;
                try {
                    typeHandler = typeHandlerFactory.getTypeHandler(type, Types.NULL);
                } catch (TypeHandlerNotFoundException e) {
                    throw new DreamRunTimeException(columnInfo.getName() + "获取类型转换器失败，" + e.getMessage(), e);
                }
                typeHandlers[i] = typeHandler;
            }
        }
        MethodInfo methodInfo = new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(NonCollection.class)
                .setColType(Integer.class)
                .setColumnNames(columnNames)
                .setColumnTypeHandlers(typeHandlers)
                .setSql(sql);
        if (sequence.isAutoIncrement(tableInfo)) {
            methodInfo.addDestroyAction(sequenceAction);
        } else {
            methodInfo.addInitAction(sequenceAction);
        }
        return methodInfo;
    }

    @Override
    protected boolean accept(WrapType wrapType) {
        return (CODE & wrapType.getCode()) > 0;
    }

    @Override
    protected Command getCommand() {
        return Command.INSERT;
    }

    protected class SequenceAction implements InitAction, DestroyAction {
        private TableInfo tableInfo;
        private Sequence sequence;

        public SequenceAction(TableInfo tableInfo, Sequence sequence) {
            this.tableInfo = tableInfo;
            this.sequence = sequence;
        }

        @Override
        public void init(MappedStatement mappedStatement, Session session) {
            sequence.sequence(tableInfo, mappedStatement, null);
        }

        @Override
        public Object destroy(Object result, MappedStatement mappedStatement, Session session) {
            sequence.sequence(tableInfo, mappedStatement, result);
            return result;
        }
    }
}
