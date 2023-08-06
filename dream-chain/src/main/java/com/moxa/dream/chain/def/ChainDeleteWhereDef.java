package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.def.DeleteWhereDef;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainDeleteWhereDef extends AbstractChainDelete implements DeleteWhereDef {
    public ChainDeleteWhereDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
