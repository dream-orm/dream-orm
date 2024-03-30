package com.dream.tdengine.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteWhereDef;
import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainDeleteWhereDef extends AbstractTdChainDeleteDef implements DeleteWhereDef {
    public TdChainDeleteWhereDef(DeleteStatement statement, FlexDeleteFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
