package com.dream.template.sequence;

import com.dream.system.config.BatchMappedStatement;
import com.dream.system.config.MappedStatement;
import com.dream.system.table.TableInfo;

import java.util.List;

public class BatchSequence implements SequenceWrapper {
    public Sequence sequence;

    public BatchSequence(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isAutoIncrement(TableInfo tableInfo) {
        return sequence.isAutoIncrement(tableInfo);
    }

    @Override
    public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object result) {
        if (!isAutoIncrement(tableInfo)) {
            BatchMappedStatement batchMappedStatement = (BatchMappedStatement) mappedStatement;
            List<MappedStatement> mappedStatementList = batchMappedStatement.getAllMappedStatementList();
            for (MappedStatement ms : mappedStatementList) {
                sequence.sequence(tableInfo, ms, result);
            }
        }
    }
}
