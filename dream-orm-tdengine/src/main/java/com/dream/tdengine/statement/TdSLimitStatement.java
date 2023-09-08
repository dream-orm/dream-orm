package com.dream.tdengine.statement;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.MyFunctionStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public class TdSLimitStatement extends MyFunctionStatement {
    private Statement first;
    private Statement second;
    private boolean offset;

    public Statement getFirst() {
        return first;
    }

    public void setFirst(Statement first) {
        this.first = wrapParent(first);
    }

    public Statement getSecond() {
        return second;
    }

    public void setSecond(Statement second) {
        this.second = wrapParent(second);
    }

    public boolean isOffset() {
        return offset;
    }

    public void setOffset(boolean offset) {
        this.offset = offset;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(first, second);
    }

    @Override
    public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
        StringBuilder builder = new StringBuilder();
        if (this.isOffset()) {
            builder.append(" SLIMIT " + toSQL.toStr(this.getFirst(), assist, invokerList));
            if (this.getSecond() != null) {
                builder.append(" SOFFSET " + toSQL.toStr(this.getSecond(), assist, invokerList));
            }
        } else {
            builder.append(" SLIMIT " + toSQL.toStr(this.getFirst(), assist, invokerList));
            if (this.getSecond() != null) {
                builder.append("," + toSQL.toStr(this.getSecond(), assist, invokerList));
            }
        }
        return builder.toString();
    }

    @Override
    public TdSLimitStatement clone() {
        TdSLimitStatement tdSLimitStatement = (TdSLimitStatement) super.clone();
        tdSLimitStatement.setFirst(clone(first));
        tdSLimitStatement.setSecond(clone(second));
        tdSLimitStatement.setOffset(offset);
        return tdSLimitStatement;
    }
}
