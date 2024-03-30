package com.dream.chain.mapper;

import com.dream.chain.def.ChainDeleteWhereDef;
import com.dream.chain.def.ChainFromDef;
import com.dream.chain.def.ChainInsertIntoColumnsDef;
import com.dream.chain.def.ChainUpdateColumnDef;
import com.dream.chain.factory.ChainFlexDeleteFactory;
import com.dream.chain.factory.ChainFlexInsertFactory;
import com.dream.chain.factory.ChainFlexQueryFactory;
import com.dream.chain.factory.ChainFlexUpdateFactory;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.TableDef;
import com.dream.flex.mapper.FlexMapper;

public class DefaultFlexChainMapper implements FlexChainMapper {
    private FlexMapper flexMapper;

    public DefaultFlexChainMapper(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public ChainFromDef select(boolean distinct, ColumnDef... columnDefs) {
        return new ChainFlexQueryFactory(flexMapper).newSelectDef().select(distinct, columnDefs);
    }

    @Override
    public ChainUpdateColumnDef update(TableDef tableDef) {
        return new ChainFlexUpdateFactory(flexMapper).newUpdateTableDef().update(tableDef);
    }

    @Override
    public ChainInsertIntoColumnsDef insertInto(TableDef tableDef) {
        return new ChainFlexInsertFactory(flexMapper).newInsertIntoTableDef().insertInto(tableDef);
    }

    @Override
    public ChainDeleteWhereDef delete(TableDef tableDef) {
        return new ChainFlexDeleteFactory(flexMapper).newDeleteTableDef().delete(tableDef);
    }
}
