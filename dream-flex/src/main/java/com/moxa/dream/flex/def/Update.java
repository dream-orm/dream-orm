package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;


public interface Update {
    UpdateStatement statement();

    UpdateCreatorFactory creatorFactory();
}
