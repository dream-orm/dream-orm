package com.dream.template.sequence;

import com.dream.system.config.MappedStatement;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.template.mapper.AbstractMapper;
import com.dream.util.common.ObjectWrapper;
import com.dream.util.exception.DreamRunTimeException;

import java.lang.reflect.Array;
import java.util.Map;

public class FetchKeySequence implements Sequence {
    private Sequence sequence;

    public FetchKeySequence(Sequence sequence) {
        if (sequence.before()) {
            this.sequence = this.new FetchKeyBeforeSequence(sequence);
        } else {
            this.sequence = new FetchKeyAfterSequence();
        }
    }

    @Override
    public boolean before() {
        return sequence.before();
    }

    @Override
    public String[] columnNames(TableInfo tableInfo) {
        return sequence.columnNames(tableInfo);
    }

    @Override
    public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg) {
        this.sequence.sequence(tableInfo, mappedStatement, arg);
    }

    class FetchKeyBeforeSequence implements Sequence {
        private Sequence sequence;

        public FetchKeyBeforeSequence(Sequence sequence) {
            this.sequence = sequence;
        }

        @Override
        public boolean before() {
            return true;
        }

        @Override
        public String[] columnNames(TableInfo tableInfo) {
            return new String[0];
        }

        @Override
        public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg) {
            sequence.sequence(tableInfo, mappedStatement, arg);
            Map<String, Object> argMap = (Map<String, Object>) mappedStatement.getArg();
            ObjectWrapper wrapper = ObjectWrapper.wrapper(argMap.get(AbstractMapper.DREAM_TEMPLATE_PARAM));
            String name = tableInfo.getPrimColumnInfo().getName();
            Object paramValue = mappedStatement.getMappedParamList().get(0).getParamValue();
            wrapper.set(name, paramValue);
        }
    }

    class FetchKeyAfterSequence implements Sequence {

        @Override
        public boolean before() {
            return false;
        }

        @Override
        public String[] columnNames(TableInfo tableInfo) {
            ColumnInfo primColumnInfo = tableInfo.getPrimColumnInfo();
            if (primColumnInfo == null) {
                throw new DreamRunTimeException("表" + tableInfo.getTable() + "不存在主键");
            }
            return new String[]{primColumnInfo.getColumn()};
        }

        @Override
        public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg) {
            Map<String, Object> argMap = (Map<String, Object>) mappedStatement.getArg();
            ObjectWrapper wrapper = ObjectWrapper.wrapper(argMap.get(AbstractMapper.DREAM_TEMPLATE_PARAM));
            String name = tableInfo.getPrimColumnInfo().getName();
            wrapper.set(name, Array.get(arg, 0));
        }
    }
}
