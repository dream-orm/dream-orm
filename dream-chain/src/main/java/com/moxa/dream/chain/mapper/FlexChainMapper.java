package com.moxa.dream.chain.mapper;

import com.moxa.dream.chain.def.ChainSelectDef;
import com.moxa.dream.flex.def.ColumnDef;

public interface FlexChainMapper {
    ChainSelectDef select(ColumnDef... columnDefs);
}
