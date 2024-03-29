package com.dream.flex.def;


public interface UpdateTableDef<T extends UpdateColumnDef> extends UpdateDef {

    default T update(TableDef tableDef) {
        statement().setTable(tableDef.getStatement().getColumn());
        return (T) creatorFactory().newUpdateColumnDef(statement());
    }
}
