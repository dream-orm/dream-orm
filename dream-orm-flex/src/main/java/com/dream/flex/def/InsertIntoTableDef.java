package com.dream.flex.def;


public interface InsertIntoTableDef<T extends InsertIntoColumnsDef> extends InsertDef {
    default T insertInto(TableDef tableDef) {
        statement().setTable(tableDef.getStatement());
        return (T) creatorFactory().newInsertIntoColumnsDef(statement());
    }
}
