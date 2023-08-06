package com.moxa.dream.chain.mapper;

import com.moxa.dream.chain.def.ChainDeleteTableDef;
import com.moxa.dream.chain.def.ChainInsertIntoTableDef;
import com.moxa.dream.chain.def.ChainSelectDef;
import com.moxa.dream.chain.def.ChainUpdateColumnDef;
import com.moxa.dream.flex.def.ColumnDef;
import com.moxa.dream.flex.def.TableDef;

public interface FlexChainMapper {
    ChainSelectDef select(ColumnDef... columnDefs);

    ChainUpdateColumnDef update(TableDef tableDef);

    ChainInsertIntoTableDef insertInto(TableDef tableDef);

    ChainDeleteTableDef delete(TableDef tableDef);
}
