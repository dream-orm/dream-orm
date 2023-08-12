package com.dream.antlr.smt;

import java.util.List;

public abstract class DDLCreateStatement extends Statement {
    private Statement statement;
    private boolean existCreate = true;

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = wrapParent(statement);
    }

    public boolean isExistCreate() {
        return existCreate;
    }

    public void setExistCreate(boolean existCreate) {
        this.existCreate = existCreate;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }

    public static class DDLCreateDatabaseStatement extends DDLCreateStatement {

    }

    public static class DDLCreateTableStatement extends DDLCreateStatement {
        private List<DDLDefineStatement> columnDefineList;
        private Statement engine;
        private Statement comment;
        private Statement defaultCharset;

        public List<DDLDefineStatement> getColumnDefineList() {
            return columnDefineList;
        }

        public void setColumnDefineList(List<DDLDefineStatement> columnDefineList) {
            this.columnDefineList = columnDefineList;
        }

        public Statement getEngine() {
            return engine;
        }

        public void setEngine(Statement engine) {
            this.engine = engine;
        }

        public Statement getComment() {
            return comment;
        }

        public void setComment(Statement comment) {
            this.comment = comment;
        }

        public Statement getDefaultCharset() {
            return defaultCharset;
        }

        public void setDefaultCharset(Statement defaultCharset) {
            this.defaultCharset = defaultCharset;
        }
    }
}
