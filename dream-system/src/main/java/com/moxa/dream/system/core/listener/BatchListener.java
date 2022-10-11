package com.moxa.dream.system.core.listener;


import com.moxa.dream.system.config.MappedStatement;

import java.util.Arrays;
import java.util.List;

public interface BatchListener extends Listener {
    @Override
    default boolean before(MappedStatement mappedStatement) {
        return before(Arrays.asList(mappedStatement));
    }

    @Override
    default Object afterReturn(Object result, MappedStatement mappedStatement) {
        return afterReturn(result, Arrays.asList(mappedStatement));
    }

    @Override
    default void exception(Exception e, MappedStatement mappedStatement) {
        exception(e, Arrays.asList(mappedStatement));
    }

    boolean before(List<MappedStatement> mappedStatements);

    Object afterReturn(Object result, List<MappedStatement> mappedStatements);

    void exception(Exception e, List<MappedStatement> mappedStatements);

}
