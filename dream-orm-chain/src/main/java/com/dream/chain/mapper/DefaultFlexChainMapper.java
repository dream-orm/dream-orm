package com.dream.chain.mapper;

import com.dream.chain.def.ChainDeleteWhereDef;
import com.dream.chain.def.ChainFromDef;
import com.dream.chain.def.ChainInsertIntoColumnsDef;
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
        return new ChainUpdateCreatorFactory(flexMapper).newUpdateTableDef().update(tableDef);
    }

    @Override
    public ChainInsertIntoColumnsDef insertInto(TableDef tableDef) {
        return new ChainInsertCreatorFactory(flexMapper).newInsertIntoTableDef().insertInto(tableDef);
    }

    @Override
    public ChainDeleteWhereDef delete(TableDef tableDef) {
        return new ChainDeleteCreatorFactory(flexMapper).newDeleteTableDef().delete(tableDef);
    }
}
