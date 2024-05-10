package com.dream.tdengine.def;

import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.def.AbstractQueryDef;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.QueryDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.struct.invoker.TakeMarkInvokerStatement;
import com.dream.system.config.Page;
import com.dream.tdengine.statement.TdQueryStatement;
import com.dream.tdengine.statement.TdSLimitStatement;
import com.dream.tdengine.statement.TdWindowStatement;

import java.util.List;

public abstract class AbstractTdChainQueryDef extends AbstractQueryDef implements QueryDef, TdChainQuery {
    protected FlexMapper flexMapper;

    public AbstractTdChainQueryDef(QueryStatement statement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(statement, flexQueryFactory);
        this.flexMapper = flexMapper;
    }

    @Override
    public <T> T one(Class<T> type) {
        return flexMapper.selectOne(this, type);
    }

    @Override
    public <T> List<T> list(Class<T> type) {
        return flexMapper.selectList(this, type);
    }

    @Override
    public <T> Page<T> page(Class<T> type, Page page) {
        return flexMapper.selectPage(this, type, page);
    }


    protected TdChainLimitDef sLimit(Integer offset, Integer rows) {
        TdSLimitStatement tdSLimitStatement = new TdSLimitStatement();
        tdSLimitStatement.setOffset(false);
        tdSLimitStatement.setFirst(new TakeMarkInvokerStatement(null, offset));
        tdSLimitStatement.setSecond(new TakeMarkInvokerStatement(null, rows));
        ((TdQueryStatement) statement()).setSlimit(tdSLimitStatement);
        return (TdChainLimitDef) creatorFactory().newLimitDef(statement());
    }

    protected TdChainLimitDef sOffset(Integer offset, Integer rows) {
        TdSLimitStatement tdSLimitStatement = new TdSLimitStatement();
        tdSLimitStatement.setOffset(true);
        tdSLimitStatement.setFirst(new TakeMarkInvokerStatement(null, rows));
        tdSLimitStatement.setSecond(new TakeMarkInvokerStatement(null, offset));
        ((TdQueryStatement) statement()).setSlimit(tdSLimitStatement);
        return (TdChainLimitDef) creatorFactory().newLimitDef(statement());
    }

    protected TdChainGroupByDef partitionBy(String... columns) {
        return new TdChainPartitionDef(statement(), creatorFactory(), flexMapper).partitionBy(columns);
    }

    protected TdChainGroupByDef partitionBy(ColumnDef... columnDefs) {
        return new TdChainPartitionDef(statement(), creatorFactory(), flexMapper).partitionBy(columnDefs);
    }

    protected TdChainOrderByDef session(String tsCol, String tsVol) {
        return session(FunctionDef.column(tsCol), tsVol);
    }

    protected TdChainOrderByDef session(ColumnDef columnDef, String tsVol) {
        TdQueryStatement tdQueryStatement = (TdQueryStatement) statement();
        TdWindowStatement.TdSessionWindowStatement tdSessionWindowStatement = new TdWindowStatement.TdSessionWindowStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(columnDef.getStatement());
        listColumnStatement.add(new SymbolStatement.LetterStatement(tsVol));
        tdSessionWindowStatement.setParamsStatement(listColumnStatement);
        tdQueryStatement.setWindnow(tdSessionWindowStatement);
        return (TdChainOrderByDef) creatorFactory().newOrderByDef(statement());
    }

    protected TdChainOrderByDef state_window(String col) {
        return state_window(FunctionDef.column(col));
    }

    protected TdChainOrderByDef state_window(ColumnDef columnDef) {
        TdQueryStatement tdQueryStatement = (TdQueryStatement) statement();
        TdWindowStatement.TdStateWindowStatement tdStateWindowStatement = new TdWindowStatement.TdStateWindowStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(columnDef.getStatement());
        tdStateWindowStatement.setParamsStatement(listColumnStatement);
        tdQueryStatement.setWindnow(tdStateWindowStatement);
        return (TdChainOrderByDef) creatorFactory().newOrderByDef(statement());
    }

    protected TdChainSlidingDef interval(String intervalVal) {
        return new TdChainIntervalDef(statement(), creatorFactory(), flexMapper).interval(intervalVal);
    }

    protected TdChainSlidingDef interval(String intervalVal, String intervalOffset) {
        return new TdChainIntervalDef(statement(), creatorFactory(), flexMapper).interval(intervalVal, intervalOffset);
    }
}
