package com.dream.chain.mapper;

import com.dream.chain.def.ChainDeleteTableDef;
import com.dream.chain.def.ChainFromDef;
import com.dream.chain.def.ChainInsertIntoTableDef;
import com.dream.chain.def.ChainUpdateColumnDef;
import com.dream.chain.factory.ChainDeleteCreatorFactory;
import com.dream.chain.factory.ChainInsertCreatorFactory;
import com.dream.chain.factory.ChainQueryCreatorFactory;
import com.dream.chain.factory.ChainUpdateCreatorFactory;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.TableDef;
import com.dream.flex.mapper.FlexMapper;

public class DefaultFlexChainMapper implements FlexChainMapper {
    private FlexMapper flexMapper;

    public DefaultFlexChainMapper(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public ChainFromDef select(ColumnDef... columnDefs) {
        return new ChainQueryCreatorFactory(flexMapper).newSelectDef().select(columnDefs);
    }

    @Override
    public ChainFromDef select(boolean distinct, ColumnDef... columnDefs) {
        return new ChainQueryCreatorFactory(flexMapper).newSelectDef().select(distinct, columnDefs);
    }

    @Override
    public ChainUpdateColumnDef update(TableDef tableDef) {
        return new ChainUpdateCreatorFactory(flexMapper).newUpdateDef().update(tableDef);
    }

    @Override
    public ChainInsertIntoTableDef insertInto(TableDef tableDef) {
        return new ChainInsertCreatorFactory(flexMapper).newInsertDef().insertInto(tableDef);
    }

    @Override
    public ChainDeleteTableDef delete(TableDef tableDef) {
        return new ChainDeleteCreatorFactory(flexMapper).newDeleteDef().delete(tableDef);
    }
}
