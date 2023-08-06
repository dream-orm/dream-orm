package com.dream.flex.def;

import com.dream.antlr.smt.CaseStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;

public class CaseColumnDef extends ColumnDef {
    public CaseColumnDef(Statement statement) {
        super(statement);
    }

    public static class Builder {

        ListColumnStatement listColumnStatement = new ListColumnStatement(" ");
        CaseStatement.WhenThenStatement whenThenStatement;
        private CaseStatement caseStatement = new CaseStatement();

        public Builder(ColumnDef columnDef) {
            caseStatement.setCaseColumn(columnDef.getStatement());
        }

        public Then when(Object column) {
            if (column instanceof Number) {
                return when(new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(column))));
            } else {
                return when(new ColumnDef(new SymbolStatement.StrStatement(String.valueOf(column))));
            }
        }

        public Then when(ColumnDef columnDef) {
            this.whenThenStatement = new CaseStatement.WhenThenStatement();
            this.whenThenStatement.setWhen(columnDef.getStatement());
            listColumnStatement.add(whenThenStatement);
            return new Then(this);
        }

        public Builder else_(Object elseValue) {
            return else_(new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(elseValue))));
        }

        public Builder else_(ColumnDef columnDef) {
            caseStatement.setElseColumn(columnDef.getStatement());
            return this;
        }

        public CaseColumnDef end() {
            caseStatement.setWhenthenList(listColumnStatement);
            return new CaseColumnDef(caseStatement);
        }

        public static class Then {

            private Builder builder;

            public Then(Builder builder) {
                this.builder = builder;
            }

            public Builder then(Object column) {
                if (column instanceof Number) {
                    return then(new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(column))));
                } else {
                    return then(new ColumnDef(new SymbolStatement.StrStatement(String.valueOf(column))));
                }
            }

            public Builder then(ColumnDef columnDef) {
                builder.whenThenStatement.setThen(columnDef.getStatement());
                return builder;
            }
        }
    }
}
