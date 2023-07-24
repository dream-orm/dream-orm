package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.*;

public class QueryDef extends AbstractQuery {
    protected QueryStatement statement;

    public QueryDef() {
        this(new QueryStatement());
    }

    public QueryDef(QueryStatement statement) {
        this.statement = statement;
    }

    public SelectDef select(ColumnDef... columnDefs) {
        SelectStatement selectStatement = new SelectStatement();
        selectStatement.setPreSelect(new PreSelectStatement());
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        if(columnDefs!=null&&columnDefs.length>0) {
            for (ColumnDef columnDef : columnDefs) {
                listColumnStatement.add(columnDef.getStatement());
            }
        }else{
            listColumnStatement.add(new SymbolStatement.LetterStatement("*"));
        }
        selectStatement.setSelectList(listColumnStatement);
        statement.setSelectStatement(selectStatement);
        return new SelectDef(statement);
    }

    @Override
    public QueryStatement getStatement() {
        return statement;
    }
}
