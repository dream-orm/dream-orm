package com.moxa.dream.chain.mapper;

import com.moxa.dream.chain.def.ChainDeleteTableDef;
import com.moxa.dream.chain.def.ChainInsertIntoTableDef;
import com.moxa.dream.chain.def.ChainSelectDef;
import com.moxa.dream.chain.def.ChainUpdateColumnDef;
import com.moxa.dream.chain.factory.ChainDeleteCreatorFactory;
import com.moxa.dream.chain.factory.ChainInsertCreatorFactory;
import com.moxa.dream.chain.factory.ChainQueryCreatorFactory;
import com.moxa.dream.chain.factory.ChainUpdateCreatorFactory;
import com.moxa.dream.flex.def.ColumnDef;
import com.moxa.dream.flex.def.TableDef;
import com.moxa.dream.flex.mapper.FlexMapper;

public class DefaultFlexChainMapper implements FlexChainMapper {
    private FlexMapper flexMapper;

    public DefaultFlexChainMapper(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public ChainSelectDef select(ColumnDef... columnDefs) {
        return new ChainQueryCreatorFactory(flexMapper).newQueryDef().select(columnDefs);
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
