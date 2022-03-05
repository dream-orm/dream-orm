package com.moxa.dream.antlr.smt;

public abstract class SymbolStatement extends Statement {
    protected String symbol;

    public SymbolStatement(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int getNameId() {
        return SymbolStatement.class.getSimpleName().hashCode();
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return true;
    }

    public static class LetterStatement extends SymbolStatement {
        private String prefix;
        private String suffix;

        public LetterStatement(String symbol) {
            super(symbol);
            int index = symbol.lastIndexOf(".");
            if (index == -1) {
                suffix = symbol;
            } else {
                prefix = symbol.substring(0, index);
                suffix = symbol.substring(index + 1);
            }
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }
    }


    public static class MarkStatement extends SymbolStatement {
        public MarkStatement(String symbol) {
            super(symbol);
        }
    }

    public static class SKipStatement extends SymbolStatement {

        public SKipStatement(String symbol) {
            super(symbol);
        }
    }

    public static abstract class ConstantStatement<T> extends SymbolStatement {

        public ConstantStatement(String symbol) {
            super(symbol);
        }

        public abstract T getValue();
    }

    public static class StrStatement extends ConstantStatement<String> {

        public StrStatement(String symbol) {
            super(symbol);
        }

        @Override
        public String getValue() {
            return symbol.substring(1, symbol.length() - 1);
        }
    }

    public static class IntStatement extends ConstantStatement<Integer> {

        public IntStatement(String symbol) {
            super(symbol);
        }

        @Override
        public Integer getValue() {
            return Integer.valueOf(symbol);
        }
    }

    public static class LongStatement extends ConstantStatement<Long> {

        public LongStatement(String symbol) {
            super(symbol);
        }

        @Override
        public Long getValue() {
            return Long.valueOf(symbol);
        }
    }

    public static class FloatStatement extends ConstantStatement<Float> {

        public FloatStatement(String symbol) {
            super(symbol);
        }

        @Override
        public Float getValue() {
            return Float.valueOf(symbol);
        }
    }

    public static class DoubleStatement extends ConstantStatement<Double> {

        public DoubleStatement(String symbol) {
            super(symbol);
        }

        @Override
        public Double getValue() {
            return Double.valueOf(symbol);
        }
    }

    public static class BooleanStatement extends ConstantStatement<Boolean> {

        public BooleanStatement(String symbol) {
            super(symbol);
        }

        @Override
        public Boolean getValue() {
            return Boolean.valueOf(symbol);
        }
    }


}
