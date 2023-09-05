package com.dream.tdengine.statement;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.MyFunctionStatement;
import com.dream.antlr.sql.ToSQL;

import java.util.List;

public abstract class TdWindowStatement extends MyFunctionStatement {
    public static class TdSessionWindowStatement extends TdWindowStatement {

        @Override
        public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
            return " SESSION(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
        }
    }

    public static class TdStateWindowStatement extends TdWindowStatement {

        @Override
        public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
            return " STATE_WINDOW(" + toSQL.toStr(paramsStatement, assist, invokerList) + ")";
        }
    }

    public static class TdIntervalWindowStatement extends TdWindowStatement {
        private TdIntervalStatement interval;
        private TdSlidingStatement sliding;
        private TdFillStatement fill;

        public TdIntervalStatement getInterval() {
            return interval;
        }

        public void setInterval(TdIntervalStatement interval) {
            this.interval = wrapParent(interval);
        }

        public TdSlidingStatement getSliding() {
            return sliding;
        }

        public void setSliding(TdSlidingStatement sliding) {
            this.sliding = wrapParent(sliding);
        }

        public TdFillStatement getFill() {
            return fill;
        }

        public void setFill(TdFillStatement fill) {
            this.fill = wrapParent(fill);
        }

        @Override
        public String toString(ToSQL toSQL, Assist assist, List<Invoker> invokerList) throws AntlrException {
            return toSQL.toStr(interval, assist, invokerList) + toSQL.toStr(sliding, assist, invokerList) + toSQL.toStr(fill, assist, invokerList);
        }

        @Override
        public TdIntervalWindowStatement clone() {
            TdIntervalWindowStatement tdIntervalWindowStatement = (TdIntervalWindowStatement) super.clone();
            tdIntervalWindowStatement.interval = (TdIntervalStatement) clone(interval);
            tdIntervalWindowStatement.sliding = (TdSlidingStatement) clone(sliding);
            tdIntervalWindowStatement.fill = (TdFillStatement) clone(fill);
            return tdIntervalWindowStatement;
        }
    }
}
