package com.dream.flex.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.factory.FlexDeleteFactory;

public interface DeleteDef {
    DeleteStatement statement();

    FlexDeleteFactory creatorFactory();
}
