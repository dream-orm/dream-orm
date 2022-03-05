package com.moxa.dream.antlr.bind;

public class ExprInfo {

    private ExprType exprType;
    private Object objInfo;
    private int start;
    private int end;

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

    @Override
    public String toString() {
        return "ExprInfo{" +
                "exprType=" + exprType +
                ", info='" + String.valueOf(objInfo) + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
