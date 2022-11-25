package com.moxa.dream.template.sequence;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.ObjectWrapper;

import java.lang.reflect.Array;
import java.util.Map;

import static com.moxa.dream.template.mapper.AbstractMapper.DREAM_TEMPLATE_PARAM;

public class MySQLSequence extends AbstractSequence {
    @Override
    public boolean before() {
        return false;
    }

    @Override
    public String[] columnNames(TableInfo tableInfo) {
        return new String[]{tableInfo.getPrimColumnInfo().getName()};
    }

    @Override
    protected Object sequence(TableInfo tableInfo, Object arg) {
        return Array.get(arg, 0);
    }
}
