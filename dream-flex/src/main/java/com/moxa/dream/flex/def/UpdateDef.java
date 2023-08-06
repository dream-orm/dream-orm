package com.moxa.dream.flex.def;

public interface UpdateDef<T extends UpdateColumnDef> extends Update {

    default T update(TableDef tableDef) {
        statement().setTable(tableDef.getStatement());
        return (T) creatorFactory().newUpdateColumnDef(statement());
    }
}
