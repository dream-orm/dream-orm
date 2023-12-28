package com.dream.flex.def;

public interface DeleteTableDef<T extends DeleteWhereDef> extends DeleteDef {

    default T delete(TableDef tableDef) {
        statement().setTable(tableDef.getStatement().getColumn());
        return (T) creatorFactory().newDeleteWhereDef(statement());
    }
}
