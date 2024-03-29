package com.dream.antlr.smt;

public abstract class CastTypeStatement extends Statement {
    private Statement statement;

    public CastTypeStatement(Statement statement) {
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
    public Statement clone() {
        CastTypeStatement castTypeStatement = (CastTypeStatement) super.clone();
        castTypeStatement.setStatement(clone(statement));
        return castTypeStatement;
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

    public static class DoubleCastStatement extends CastTypeStatement {

        public DoubleCastStatement(Statement statement) {
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

        @Override
        public DecimalCastStatement clone() {
            DecimalCastStatement decimalCastStatement = (DecimalCastStatement) super.clone();
            decimalCastStatement.setParentStatement(clone(paramStatement));
            return decimalCastStatement;
        }
    }


}
