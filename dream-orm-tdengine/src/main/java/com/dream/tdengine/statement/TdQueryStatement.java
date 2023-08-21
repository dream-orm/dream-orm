package com.dream.tdengine.statement;

import com.dream.antlr.smt.QueryStatement;

public class TdQueryStatement extends QueryStatement {

    private TdPartitionStatement partitionBy;

    private TdWindowStatement windnow;

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

    public TdWindowStatement getWindnow() {
        return windnow;
    }

    public void setWindnow(TdWindowStatement windnow) {
        this.windnow = windnow;
    }

    public TdSLimitStatement getSlimit() {
        return slimit;
    }

    public void setSlimit(TdSLimitStatement slimit) {
        this.slimit = slimit;
    }
}
