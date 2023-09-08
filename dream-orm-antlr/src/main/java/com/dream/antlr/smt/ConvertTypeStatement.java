package com.dream.antlr.smt;

public abstract class ConvertTypeStatement extends Statement {
    private Statement statement;

    public ConvertTypeStatement(Statement statement) {
        setStatement(statement);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = wrapParent(statement);
    }

    @Override
    public ConvertTypeStatement clone() {
        ConvertTypeStatement convertTypeStatement = (ConvertTypeStatement) super.clone();
        convertTypeStatement.setStatement(clone(statement));
        return convertTypeStatement;
    }

    public static class CharConvertStatement extends ConvertTypeStatement {

        public CharConvertStatement(Statement statement) {
            super(statement);
        }
    }

    public static class DateConvertStatement extends ConvertTypeStatement {

        public DateConvertStatement(Statement statement) {
            super(statement);
        }
    }

    public static class TimeConvertStatement extends ConvertTypeStatement {

        public TimeConvertStatement(Statement statement) {
            super(statement);
        }
    }

    public static class DateTimeConvertStatement extends ConvertTypeStatement {

        public DateTimeConvertStatement(Statement statement) {
            super(statement);
        }
    }

    public static class SignedConvertStatement extends ConvertTypeStatement {

        public SignedConvertStatement(Statement statement) {
            super(statement);
        }
    }

    public static class FloatConvertStatement extends ConvertTypeStatement {

        public FloatConvertStatement(Statement statement) {
            super(statement);
        }
    }

    public static class DecimalConvertStatement extends ConvertTypeStatement {
        public Statement paramStatement;

        public DecimalConvertStatement(Statement statement) {
            super(statement);
        }

        public Statement getParamStatement() {
            return paramStatement;
        }

        public void setParamStatement(Statement paramStatement) {
            this.paramStatement = wrapParent(paramStatement);
        }

        @Override
        public DecimalConvertStatement clone() {
            DecimalConvertStatement decimalConvertStatement = (DecimalConvertStatement) super.clone();
            decimalConvertStatement.setParamStatement(clone(paramStatement));
            return decimalConvertStatement;
        }
    }


}
