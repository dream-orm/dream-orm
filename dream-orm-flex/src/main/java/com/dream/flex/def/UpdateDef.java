package com.dream.flex.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.factory.UpdateCreatorFactory;


public interface UpdateDef {
    UpdateStatement statement();

    UpdateCreatorFactory creatorFactory();
}
