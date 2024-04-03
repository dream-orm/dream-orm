package com.dream.flex.def;

import com.dream.flex.factory.FlexDeleteFactory;
import com.dream.struct.command.Delete;

public interface DeleteDef extends Delete {
    FlexDeleteFactory creatorFactory();
}
