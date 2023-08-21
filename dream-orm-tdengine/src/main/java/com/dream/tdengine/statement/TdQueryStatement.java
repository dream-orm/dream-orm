package com.dream.tdengine.statement;

import com.dream.antlr.smt.QueryStatement;

public class TdQueryStatement extends QueryStatement {

    private TdPartitionStatement partitionBy;

    private TdIntervalStatement interval;

    private TdSLimitStatement slimit;

    @Override
    public int getNameId() {
        return QueryStatement.class.getSimpleName().hashCode();
    }

    public TdPartitionStatement getPartitionBy() {
        return partitionBy;
    }

    public void setPartitionBy(TdPartitionStatement partitionBy) {
        this.partitionBy = partitionBy;
    }

    public TdIntervalStatement getInterval() {
        return interval;
    }

    public void setInterval(TdIntervalStatement interval) {
        this.interval = interval;
    }

    public TdSLimitStatement getSlimit() {
        return slimit;
    }

    public void setSlimit(TdSLimitStatement slimit) {
        this.slimit = slimit;
    }
}
