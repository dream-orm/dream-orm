package com.dream.flex.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.factory.FlexUpdateFactory;


public interface UpdateDef {
    UpdateStatement statement();

    FlexUpdateFactory creatorFactory();
}
