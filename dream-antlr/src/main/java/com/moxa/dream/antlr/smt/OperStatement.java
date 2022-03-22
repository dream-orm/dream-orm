package com.moxa.dream.antlr.smt;

public abstract class OperStatement extends Statement {
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return parentStatement.isNeedInnerCache();
    }

    public static class ADDStatement extends OperStatement {
        public ADDStatement() {
            setLevel(11);
        }
    }

    public static class SUBStatement extends OperStatement {
        public SUBStatement() {
            setLevel(11);
        }
    }

    public static class LTStatement extends OperStatement {
        public LTStatement() {
            setLevel(7);
        }
    }

    public static class STARStatement extends OperStatement {
        public STARStatement() {
            setLevel(12);
        }
    }

    public static class DIVIDEStatement extends OperStatement {
        public DIVIDEStatement() {
            setLevel(12);
        }
    }

    public static class MODStatement extends OperStatement {
        public MODStatement() {
            setLevel(12);
        }
    }

    public static class LLMStatement extends OperStatement {
        public LLMStatement() {
            setLevel(10);
        }
    }

    public static class RRMStatement extends OperStatement {
        public RRMStatement() {
            setLevel(10);
        }
    }

    public static class ANDStatement extends OperStatement {
        public ANDStatement() {
            setLevel(2);
        }
    }

    public static class BITANDStatement extends OperStatement {
        public BITANDStatement() {
            setLevel(6);
        }
    }

    public static class BITORStatement extends OperStatement {
        public BITORStatement() {
            setLevel(5);
        }
    }

    public static class BITXORStatement extends OperStatement {
        public BITXORStatement() {
            setLevel(4);
        }
    }

    public static class ORStatement extends OperStatement {
        public ORStatement() {
            setLevel(1);
        }
    }

    public static class GTStatement extends OperStatement {
        public GTStatement() {
            setLevel(7);
        }
    }

    public static class LEQStatement extends OperStatement {
        public LEQStatement() {
            setLevel(7);
        }
    }

    public static class GEQStatement extends OperStatement {
        public GEQStatement() {
            setLevel(7);
        }
    }

    public static class EQStatement extends OperStatement {
        public EQStatement() {
            setLevel(7);
        }
    }

    public static class NEQStatement extends OperStatement {
        public NEQStatement() {
            setLevel(7);
        }
    }

    public static class ISStatement extends OperStatement {
        public ISStatement() {
            setLevel(7);
        }
    }

    public static class INStatement extends OperStatement {
        public INStatement() {
            setLevel(7);
        }
    }

    public static class NOTStatement extends OperStatement {
        public NOTStatement() {
            setLevel(3);
        }
    }

    public static class EXISTSStatement extends OperStatement {
        public EXISTSStatement() {
            setLevel(7);
        }
    }


    public static class LIKEStatement extends OperStatement {
        public LIKEStatement() {
            setLevel(7);
        }
    }

    public static class BETWEENStatement extends OperStatement {
        public BETWEENStatement() {
            setLevel(7);
        }
    }


}
