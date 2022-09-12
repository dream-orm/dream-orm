package com.moxa.dream.template.mapper.fetch;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.annotation.Sequence;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public abstract class SequenceFetchKey implements FetchKey {
    protected String sequenceColumn;
    protected String fieldName;
    protected Configuration configuration;

    public SequenceFetchKey(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Action[] initActionList(TableInfo tableInfo) {
        ColumnInfo primColumnInfo = tableInfo.getPrimColumnInfo();
        Sequence sequenceAnnotation = primColumnInfo.getAnnotation(Sequence.class);
        ObjectUtil.requireNonNull(sequenceAnnotation, "数据表'" + tableInfo.getTable() + "'未支持序列");
        fieldName = primColumnInfo.getName();
        this.sequenceColumn = sequenceAnnotation.value();
        return new Action[]{new FetchKeyAction(fieldName)};
    }

    protected MappedStatement getMappedStatement(Object arg) throws Exception {
        Field field = arg.getClass().getDeclaredField(fieldName);
        Type genericType = field.getGenericType();
        MethodInfo methodInfo = new MethodInfo.Builder(configuration)
                .rowType(ReflectUtil.getRowType(genericType))
                .colType(ReflectUtil.getColType(genericType))
                .sql("@(" + getSequenceSql(sequenceColumn) + ")")
                .build();
        MappedStatement mappedStatement = configuration.getDialectFactory().compile(methodInfo, null);
        mappedStatement.setCache(false);
        mappedStatement.setCommand(Command.QUERY);
        return mappedStatement;
    }

    protected abstract String getSequenceSql(String sequenceColumn);

    class FetchKeyAction implements Action {

        private MappedStatement mappedStatement;
        private String property;

        public FetchKeyAction(String property) {
            this.property = property;
        }

        @Override
        public void doAction(Executor executor, Object arg) throws Exception {
            if (mappedStatement == null) {
                synchronized (this) {
                    if (mappedStatement == null) {
                        mappedStatement = SequenceFetchKey.this.getMappedStatement(arg);
                    }
                }
            }
            Object result = executor.query(mappedStatement);
            if (!ObjectUtil.isNull(property)) {
                ObjectWrapper.wrapper(arg).set(property, result);
            }
        }
    }
}
