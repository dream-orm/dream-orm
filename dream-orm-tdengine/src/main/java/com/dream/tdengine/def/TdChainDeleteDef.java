package com.dream.tdengine.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainDeleteDef extends AbstractTdChainDeleteDef implements DeleteDef {
    public TdChainDeleteDef(FlexDeleteFactory creatorFactory, FlexMapper flexMapper) {
        super(new DeleteStatement(), creatorFactory, flexMapper);
    }
}
