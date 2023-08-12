package com.dream.antlr.smt;

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
        private ListColumnStatement columnDefineList;
        private Statement engine;
        private Statement comment;
        private Statement defaultCharset;

        public ListColumnStatement getColumnDefineList() {
            return columnDefineList;
        }

        public void setColumnDefineList(ListColumnStatement columnDefineList) {
            this.columnDefineList = wrapParent(columnDefineList);
        }

        public Statement getEngine() {
            return engine;
        }

        public void setEngine(Statement engine) {
            this.engine = wrapParent(engine);
        }

        public Statement getComment() {
            return comment;
        }

        public void setComment(Statement comment) {
            this.comment = wrapParent(comment);
        }

        public Statement getDefaultCharset() {
            return defaultCharset;
        }

        public void setDefaultCharset(Statement defaultCharset) {
            this.defaultCharset = wrapParent(defaultCharset);
        }
    }
}
