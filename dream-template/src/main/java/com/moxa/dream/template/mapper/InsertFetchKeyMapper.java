package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.system.typehandler.factory.TypeHandlerFactory;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.template.sequence.Sequence;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InsertFetchKeyMapper extends InsertMapper {
    private Sequence sequence;

    public InsertFetchKeyMapper(Session session, Sequence sequence) {
        super(session);
        this.sequence = sequence;
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, String sql) {
        ColumnInfo primColumnInfo = tableInfo.getPrimColumnInfo();
        if (primColumnInfo == null) {
            throw new DreamRunTimeException(tableInfo.getTable() + "不存在主键");
        }
        List<Action> initActionList = new ArrayList<>();
        List<Action> destroyActionList = new ArrayList<>();
        sequence.init(tableInfo);
        String[] columnNames = sequence.columnNames(tableInfo);
        TypeHandler[] typeHandlers = null;
        SequenceAction sequenceAction = new SequenceAction(tableInfo, sequence);
        if (sequence.before()) {
            initActionList.add(sequenceAction);
        } else {
            destroyActionList.add(sequenceAction);
        }
        if (columnNames != null && columnNames.length > 0) {
            typeHandlers = new TypeHandler[columnNames.length];
            TypeHandlerFactory typeHandlerFactory = configuration.getTypeHandlerFactory();
            for (int i = 0; i < columnNames.length; i++) {
                String column = columnNames[i];
                String fieldName = tableInfo.getFieldName(column);
                if (fieldName == null) {
                    throw new DreamRunTimeException("表" + tableInfo.getTable() + "不存在字段" + column);
                }
                Class<?> type = tableInfo.getColumnInfo(fieldName).getField().getType();
                TypeHandler typeHandler = typeHandlerFactory.getTypeHandler(type, Types.NULL);
                typeHandlers[i] = typeHandler;
            }
        }
        return new MethodInfo.Builder(configuration)
                .rowType(NonCollection.class)
                .colType(Integer.class)
                .columnNames(columnNames, typeHandlers)
                .initActionList(initActionList.toArray(new Action[0]))
                .destroyActionList(destroyActionList.toArray(new Action[0]))
                .sql(sql)
                .build();
    }

    class SequenceAction implements Action {
        private TableInfo tableInfo;
        private Sequence sequence;

        public SequenceAction(TableInfo tableInfo, Sequence sequence) {
            this.tableInfo = tableInfo;
            this.sequence = sequence;
        }

        @Override
        public void doAction(Session session, MappedStatement mappedStatement, Object arg) throws Exception {
            sequence.sequence(tableInfo,mappedStatement, arg);
        }
    }
}
