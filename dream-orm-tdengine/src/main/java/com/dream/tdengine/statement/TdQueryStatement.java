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
        this.partitionBy = wrapParent(partitionBy);
    }

    public TdWindowStatement getWindnow() {
        return windnow;
    }

    public void setWindnow(TdWindowStatement windnow) {
        this.windnow = wrapParent(windnow);
    }

    public TdSLimitStatement getSlimit() {
        return slimit;
    }

    public void setSlimit(TdSLimitStatement slimit) {
        this.slimit = wrapParent(slimit);
    }

    @Override
    public TdQueryStatement clone() {
        TdQueryStatement tdQueryStatement = (TdQueryStatement) super.clone();
        tdQueryStatement.partitionBy = (TdPartitionStatement) clone(partitionBy);
        tdQueryStatement.windnow = (TdWindowStatement) clone(windnow);
        tdQueryStatement.slimit = (TdSLimitStatement) clone(slimit);
        return tdQueryStatement;
    }
}
