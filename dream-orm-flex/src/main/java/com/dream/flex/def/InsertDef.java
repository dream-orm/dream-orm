package com.dream.flex.def;

import com.dream.flex.factory.FlexInsertFactory;
import com.dream.regular.command.Insert;


public interface InsertDef extends Insert {
    FlexInsertFactory creatorFactory();
}
