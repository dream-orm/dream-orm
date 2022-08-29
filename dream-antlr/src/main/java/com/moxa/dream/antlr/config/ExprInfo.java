package com.moxa.dream.antlr.config;

public class ExprInfo {

    private final Object objInfo;
    private final int start;
    private final int end;
    private ExprType exprType;

    public ExprInfo(ExprType exprType, String info) {
        this(exprType, info, 0, 0);
    }

    public ExprInfo(ExprType exprType, Object objInfo, int start, int end) {
        this.exprType = exprType;
        this.objInfo = objInfo;
        this.start = start;
        this.end = end;
    }

    public ExprType getExprType() {
        return exprType;
    }

    public void setExprType(ExprType exprType) {
        this.exprType = exprType;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getInfo() {
        return String.valueOf(objInfo);
    }

    public Object getObjInfo() {
        return objInfo;
    }
}
