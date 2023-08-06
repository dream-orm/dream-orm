package com.moxa.dream.chain.mapper;

import com.moxa.dream.chain.def.ChainQueryCreatorFactory;
import com.moxa.dream.chain.def.ChainSelectDef;
import com.moxa.dream.flex.def.ColumnDef;
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
}
