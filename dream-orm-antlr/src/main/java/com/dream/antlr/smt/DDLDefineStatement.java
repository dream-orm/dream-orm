package com.dream.antlr.smt;

import com.dream.antlr.config.ExprType;

public abstract class DDLDefineStatement extends Statement {
    @Override
    protected Boolean isNeedInnerCache() {
        return true;
    }

    public static class DDLPrimaryKeyDefineStatement extends DDLDefineStatement {
        private Statement constraint;
        private ListColumnStatement primaryKeys;

        public Statement getConstraint() {
            return constraint;
        }

        public void setConstraint(Statement constraint) {
            this.constraint = constraint;
        }

        public ListColumnStatement getPrimaryKeys() {
            return primaryKeys;
        }

        public void setPrimaryKeys(ListColumnStatement primaryKeys) {
            this.primaryKeys = primaryKeys;
        }

        @Override
        public DDLPrimaryKeyDefineStatement clone() {
            DDLPrimaryKeyDefineStatement ddlPrimaryKeyDefineStatement = (DDLPrimaryKeyDefineStatement) super.clone();
            ddlPrimaryKeyDefineStatement.constraint = clone(constraint);
            ddlPrimaryKeyDefineStatement.primaryKeys = (ListColumnStatement) clone(primaryKeys);
            return ddlPrimaryKeyDefineStatement;
        }
    }

    public static class DDLForeignKeyDefineStatement extends DDLDefineStatement {
        private Statement constraint;
        private Statement foreignKey;
        private Statement foreignTable;
        private Statement foreignColumn;

        public Statement getConstraint() {
            return constraint;
        }

        public void setConstraint(Statement constraint) {
            this.constraint = wrapParent(constraint);
        }

        public Statement getForeignKey() {
            return foreignKey;
        }

        public void setForeignKey(Statement foreignKey) {
            this.foreignKey = wrapParent(foreignKey);
        }

        public Statement getForeignTable() {
            return foreignTable;
        }

        public void setForeignTable(Statement foreignTable) {
            this.foreignTable = wrapParent(foreignTable);
        }

        public Statement getForeignColumn() {
            return foreignColumn;
        }

        public void setForeignColumn(Statement foreignColumn) {
            this.foreignColumn = wrapParent(foreignColumn);
        }

        @Override
        public DDLForeignKeyDefineStatement clone() {
            DDLForeignKeyDefineStatement ddlForeignKeyDefineStatement = (DDLForeignKeyDefineStatement) super.clone();
            ddlForeignKeyDefineStatement.constraint = clone(constraint);
            ddlForeignKeyDefineStatement.foreignKey = clone(foreignKey);
            ddlForeignKeyDefineStatement.foreignTable = clone(foreignTable);
            ddlForeignKeyDefineStatement.foreignColumn = clone(foreignColumn);
            return ddlForeignKeyDefineStatement;
        }
    }

    public static class DDLColumnDefineStatement extends DDLDefineStatement {
        private Statement column;
        private ExprType columnType;
        private ListColumnStatement columnTypeParamList;
        private Statement defaultValue;
        private Statement comment;
        private boolean nullFlag = true;
        private boolean autoIncrement = false;
        private boolean primaryKey = false;

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

        public Statement getComment() {
            return comment;
        }

        public void setComment(Statement comment) {
            this.comment = wrapParent(comment);
        }

        public boolean isNullFlag() {
            return nullFlag;
        }

        public void setNullFlag(boolean nullFlag) {
            this.nullFlag = nullFlag;
        }

        public boolean isAutoIncrement() {
            return autoIncrement;
        }

        public void setAutoIncrement(boolean autoIncrement) {
            this.autoIncrement = autoIncrement;
        }

        public boolean isPrimaryKey() {
            return primaryKey;
        }

        public void setPrimaryKey(boolean primaryKey) {
            this.primaryKey = primaryKey;
        }

        @Override
        public DDLColumnDefineStatement clone() {
            DDLColumnDefineStatement ddlColumnDefineStatement = (DDLColumnDefineStatement) super.clone();
            ddlColumnDefineStatement.column = clone(column);
            ddlColumnDefineStatement.columnType = columnType;
            ddlColumnDefineStatement.columnTypeParamList = (ListColumnStatement) clone(columnTypeParamList);
            ddlColumnDefineStatement.defaultValue = clone(defaultValue);
            ddlColumnDefineStatement.comment = clone(comment);
            ddlColumnDefineStatement.nullFlag = nullFlag;
            ddlColumnDefineStatement.autoIncrement = autoIncrement;
            ddlColumnDefineStatement.primaryKey = primaryKey;
            return ddlColumnDefineStatement;
        }
    }
}
