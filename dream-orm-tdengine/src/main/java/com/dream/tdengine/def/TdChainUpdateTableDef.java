package com.dream.tdengine.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateTableDef;
import com.dream.flex.factory.FlexUpdateFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainUpdateTableDef extends AbstractTdChainUpdateDef implements UpdateTableDef<TdChainUpdateColumnDef> {
    public TdChainUpdateTableDef(FlexUpdateFactory creatorFactory, FlexMapper flexMapper) {
        super(new UpdateStatement(), creatorFactory, flexMapper);
    }
}
