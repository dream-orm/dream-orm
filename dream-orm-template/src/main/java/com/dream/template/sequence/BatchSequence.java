package com.dream.template.sequence;

import com.dream.system.config.BatchMappedStatement;
import com.dream.system.config.MappedStatement;
import com.dream.system.table.TableInfo;

import java.util.List;

public class BatchSequence implements Sequence {
    public Sequence sequence;

    public BatchSequence(Sequence sequence) {
        if (sequence.before()) {
            this.sequence = new BatchBeforeSequence(sequence);
        } else {
            this.sequence = new BatchAfterSequence(sequence);
        }
    }

    @Override
    public boolean before() {
        return sequence.before();
    }

    @Override
    public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg) {
        this.sequence.sequence(tableInfo, mappedStatement, arg);
    }

    class BatchBeforeSequence implements Sequence {
        public Sequence sequence;

        public BatchBeforeSequence(Sequence sequence) {
            this.sequence = sequence;
        }

        @Override
        public boolean before() {
            return true;
        }

        @Override
        public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg) {
            BatchMappedStatement batchMappedStatement = (BatchMappedStatement) mappedStatement;
            List<MappedStatement> mappedStatementList = batchMappedStatement.getMappedStatementList();
            for (MappedStatement ms : mappedStatementList) {
                sequence.sequence(tableInfo, ms, arg);
            }
        }
    }

    class BatchAfterSequence implements Sequence {
        public Sequence sequence;

        public BatchAfterSequence(Sequence sequence) {
            this.sequence = sequence;
        }

        @Override
        public boolean before() {
            return false;
        }

        @Override
        public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg) {

        }
    }
}
