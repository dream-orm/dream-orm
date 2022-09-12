package com.moxa.dream.template.mapper.fetch;

import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.table.TableInfo;

public interface FetchKey {
    default Action[] initActionList(TableInfo tableInfo) {
        return new Action[0];
    }

    default Action[] destroyActionList(TableInfo tableInfo) {
        return new Action[0];
    }

    default String[] getColumnNames(TableInfo tableInfo) {
        return new String[0];
    }
}
