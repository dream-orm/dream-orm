package com.moxa.dream.template.sequence;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.mapper.AbstractMapper;
import com.moxa.dream.util.common.ObjectWrapper;

import java.util.Map;

public abstract class AbstractSequence implements Sequence {
    @Override
    public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg) {
        Map<String, Object> argMap = (Map<String, Object>) mappedStatement.getArg();
        ObjectWrapper wrapper = ObjectWrapper.wrapper(argMap.get(AbstractMapper.DREAM_TEMPLATE_PARAM));
        String name = tableInfo.getPrimColumnInfo().getName();
        Object sequence = sequence(tableInfo, arg);
        wrapper.set(name, sequence);
    }

    protected abstract Object sequence(TableInfo tableInfo, Object arg);

}
