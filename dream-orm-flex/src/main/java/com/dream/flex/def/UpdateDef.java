package com.dream.flex.def;

import com.dream.flex.factory.FlexUpdateFactory;
import com.dream.struct.command.Update;


public interface UpdateDef extends Update {
    FlexUpdateFactory creatorFactory();
}
