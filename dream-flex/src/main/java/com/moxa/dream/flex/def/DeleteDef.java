package com.moxa.dream.flex.def;

public interface DeleteDef<T extends DeleteTableDef> extends Delete {

    default T delete(TableDef tableDef) {
        statement().setTable(tableDef.getStatement());
        return (T) creatorFactory().newDeleteTableDef(statement());
    }
}
