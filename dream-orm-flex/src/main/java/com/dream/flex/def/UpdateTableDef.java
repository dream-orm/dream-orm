package com.dream.flex.def;

public interface UpdateTableDef<T extends UpdateDefColumnDef> extends UpdateDef {

    default T update(TableDef tableDef) {
        statement().setTable(tableDef.getStatement());
        return (T) creatorFactory().newUpdateColumnDef(statement());
    }
}
