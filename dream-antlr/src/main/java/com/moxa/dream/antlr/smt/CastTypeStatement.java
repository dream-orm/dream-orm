package com.moxa.dream.antlr.smt;

public abstract class CastTypeStatement extends Statement {
    private final Statement statement;

    public CastTypeStatement(Statement statement) {
        this.statement = statement;
        if (statement != null) {
            statement.parentStatement = this;
        }
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }

    public Statement getStatement() {
        return statement;
    }

    public static class CharCastStatement extends CastTypeStatement {

        public CharCastStatement(Statement statement) {
            super(statement);
        }
    }

    public static class DateCastStatement extends CastTypeStatement {

        public DateCastStatement(Statement statement) {
            super(statement);
        }
    }

    public static class TimeCastStatement extends CastTypeStatement {

        public TimeCastStatement(Statement statement) {
            super(statement);
        }
    }

    public static class DateTimeCastStatement extends CastTypeStatement {

        public DateTimeCastStatement(Statement statement) {
            super(statement);
        }
    }

    public static class SignedCastStatement extends CastTypeStatement {

        public SignedCastStatement(Statement statement) {
            super(statement);
        }
    }

    public static class FloatCastStatement extends CastTypeStatement {

        public FloatCastStatement(Statement statement) {
            super(statement);
        }
    }

    public static class DecimalCastStatement extends CastTypeStatement {
        public Statement paramStatement;

        public DecimalCastStatement(Statement statement) {
            super(statement);
        }

        public Statement getParamStatement() {
            return paramStatement;
        }

        public void setParamStatement(Statement paramStatement) {
            this.paramStatement = paramStatement;
        }
    }


}
