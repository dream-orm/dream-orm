package com.dream.antlr.smt;


import com.dream.antlr.config.ExprType;

public abstract class DDLAlterStatement extends Statement {
    private Statement table;

    public Statement getTable() {
        return table;
    }

    public void setTable(Statement table) {
        this.table = wrapParent(table);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return true;
    }

    public static class DDLAlterRenameStatement extends DDLAlterStatement {
        private Statement newTable;

        public Statement getNewTable() {
            return newTable;
        }

        public void setNewTable(Statement newTable) {
            this.newTable = wrapParent(newTable);
        }
    }

    public static class DDLAlterAddStatement extends DDLAlterStatement {
        private Statement column;
        private ExprType columnType;
        private Statement defaultValue;
        private boolean nullFlag = true;
        private ListColumnStatement columnTypeParamList;

        public Statement getColumn() {
            return column;
        }

        public void setColumn(Statement column) {
            this.column = wrapParent(column);
        }

        public ExprType getColumnType() {
            return columnType;
        }

        public void setColumnType(ExprType columnType) {
            this.columnType = columnType;
        }

        public ListColumnStatement getColumnTypeParamList() {
            return columnTypeParamList;
        }

        public void setColumnTypeParamList(ListColumnStatement columnTypeParamList) {
            this.columnTypeParamList = wrapParent(columnTypeParamList);
        }

        public Statement getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(Statement defaultValue) {
            this.defaultValue = wrapParent(defaultValue);
        }

        public boolean isNullFlag() {
            return nullFlag;
        }

        public void setNullFlag(boolean nullFlag) {
            this.nullFlag = nullFlag;
        }
    }

    public static class DDLAlterDropStatement extends DDLAlterStatement {
        private Statement column;

        public Statement getColumn() {
            return column;
        }

        public void setColumn(Statement column) {
            this.column = wrapParent(column);
        }
    }

    public static class DDLAlterModifyStatement extends DDLAlterStatement {
        private Statement column;
        private ExprType columnType;
        private Statement defaultValue;
        private boolean nullFlag = true;
        private ListColumnStatement columnTypeParamList;

        public Statement getColumn() {
            return column;
        }

        public void setColumn(Statement column) {
            this.column = wrapParent(column);
        }

        public ExprType getColumnType() {
            return columnType;
        }

        public void setColumnType(ExprType columnType) {
            this.columnType = columnType;
        }

        public ListColumnStatement getColumnTypeParamList() {
            return columnTypeParamList;
        }

        public void setColumnTypeParamList(ListColumnStatement columnTypeParamList) {
            this.columnTypeParamList = wrapParent(columnTypeParamList);
        }

        public Statement getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(Statement defaultValue) {
            this.defaultValue = wrapParent(defaultValue);
        }

        public boolean isNullFlag() {
            return nullFlag;
        }

        public void setNullFlag(boolean nullFlag) {
            this.nullFlag = nullFlag;
        }
    }
}
