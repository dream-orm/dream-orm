package com.dream.flex.def;


public interface InsertDef<T extends InsertIntoTableDef> extends Insert {
    default T insertInto(TableDef tableDef) {
        statement().setTable(tableDef.getStatement());
        return (T) creatorFactory().newInsertIntoTableDef(statement());
    }
}
