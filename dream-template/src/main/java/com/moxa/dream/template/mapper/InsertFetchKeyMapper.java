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
import com.moxa.dream.template.annotation.Sequence;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InsertFetchKeyMapper extends InsertMapper {
    public InsertFetchKeyMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo getMethodInfo(Configuration configuration, TableInfo tableInfo, String sql) {
        ColumnInfo primColumnInfo = tableInfo.getPrimColumnInfo();
        if (primColumnInfo == null) {
            throw new DreamRunTimeException(tableInfo.getTable() + "不存在主键");
        }
        Sequence sequenceAnnotation = primColumnInfo.getAnnotation(Sequence.class);
        List<Action> initActionList = new ArrayList<>();
        List<Action> destroyActionList = new ArrayList<>();
        String[] columnNames = null;
        TypeHandler[] typeHandlers = null;
        if (sequenceAnnotation != null) {
            Class<? extends com.moxa.dream.template.sequence.Sequence> sequenceClass = sequenceAnnotation.value();
            com.moxa.dream.template.sequence.Sequence sequence = ReflectUtil.create(sequenceClass);
            sequence.init(tableInfo);
            columnNames = sequence.columnNames();
            SequenceAction sequenceAction = new SequenceAction(sequence, primColumnInfo.getName());
            if (sequence.before()) {
                initActionList.add(sequenceAction);
            } else {
                destroyActionList.add(sequenceAction);
            }
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
        private com.moxa.dream.template.sequence.Sequence sequence;
        private String fieldName;

        public SequenceAction(com.moxa.dream.template.sequence.Sequence sequence, String fieldName) {
            this.sequence = sequence;
            this.fieldName = fieldName;
        }

        @Override
        public void doAction(Session session, MappedStatement mappedStatement, Object arg) throws Exception {
            Map<String, Object> argMap = (Map<String, Object>) mappedStatement.getArg();
            sequence.sequence(ObjectWrapper.wrapper(argMap.get(DREAM_TEMPLATE_PARAM)), fieldName, arg);
        }
    }
}
