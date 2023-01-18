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

    public String getValue() {
        return symbol;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return true;
    }

    public static class LetterStatement extends SymbolStatement {
        public LetterStatement(String symbol) {
            super(symbol);
        }
    }

    public static class MarkStatement extends SymbolStatement {
        public MarkStatement() {
            super("?");
        }
    }

    public static class StrStatement extends SymbolStatement {

        public StrStatement(String symbol) {
            super(symbol);
        }

        @Override
        public String getSymbol() {
            return "'" + getValue() + "'";
        }
    }


    public static class JavaStrStatement extends SymbolStatement {
        public JavaStrStatement(String symbol) {
            super(symbol);
        }

        @Override
        public String getSymbol() {
            return "\"" + getValue() + "\"";
        }
    }

    public static class SingleMarkStatement extends SymbolStatement {
        public SingleMarkStatement(String symbol) {
            super(symbol);
        }

        @Override
        public int getNameId() {
            return SingleMarkStatement.class.getSimpleName().hashCode();
        }

        @Override
        public String getSymbol() {
            return "`" + getValue() + "`";
        }
    }

    public static class IntStatement extends SymbolStatement {

        public IntStatement(String symbol) {
            super(symbol);
        }

    }

    public static class LongStatement extends SymbolStatement {

        public LongStatement(String symbol) {
            super(symbol);
        }
    }

    public static class FloatStatement extends SymbolStatement {

        public FloatStatement(String symbol) {
            super(symbol);
        }
    }

    public static class DoubleStatement extends SymbolStatement {

        public DoubleStatement(String symbol) {
            super(symbol);
        }
    }
}
