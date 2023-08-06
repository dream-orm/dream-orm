package com.dream.chain.mapper;

import com.dream.chain.def.ChainDeleteTableDef;
import com.dream.chain.def.ChainInsertIntoTableDef;
import com.dream.chain.def.ChainSelectDef;
import com.dream.chain.def.ChainUpdateColumnDef;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.TableDef;

public interface FlexChainMapper {
    ChainSelectDef select(ColumnDef... columnDefs);

    ChainUpdateColumnDef update(TableDef tableDef);

    ChainInsertIntoTableDef insertInto(TableDef tableDef);

    ChainDeleteTableDef delete(TableDef tableDef);
}
