package com.dream.tdengine.def;

import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.tdengine.statement.TdMyFunctionStatement;

public class TdFunctionDef extends FunctionDef {
    public static ColumnDef first(String col) {
        return first(FunctionDef.col(col));
    }

    public static ColumnDef first(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("FIRST"), columnDef);
    }

    public static ColumnDef last(String col) {
        return last(FunctionDef.col(col));
    }

    public static ColumnDef last(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("LAST"), columnDef);
    }

    public static ColumnDef last_row(String col) {
        return last_row(FunctionDef.col(col));
    }

    public static ColumnDef last_row(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("LAST_ROW"), columnDef);
    }

    public static ColumnDef mode(String col) {
        return mode(FunctionDef.col(col));
    }

    public static ColumnDef mode(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("LAST_ROW"), columnDef);
    }

    public static ColumnDef sample(String col, int k) {
        return sample(FunctionDef.col(col), k);
    }

    public static ColumnDef sample(ColumnDef columnDef, int k) {
        return functionDef(new TdMyFunctionStatement("LAST_ROW"), columnDef, FunctionDef.col(k));
    }

    public static ColumnDef tail(String col, int k) {
        return tail(col, k, null);
    }

    public static ColumnDef tail(String col, int k, Integer offset) {
        return tail(FunctionDef.col(col), k, offset);
    }

    public static ColumnDef tail(ColumnDef columnDef, int k) {
        return tail(columnDef, k, null);
    }

    public static ColumnDef tail(ColumnDef columnDef, int k, Integer offset) {
        if (offset != null) {
            return functionDef(new TdMyFunctionStatement("TAIL"), columnDef, FunctionDef.col(k), FunctionDef.col(offset));
        } else {
            return functionDef(new TdMyFunctionStatement("TAIL"), columnDef, FunctionDef.col(k));
        }
    }

    public static ColumnDef top(String col, int k) {
        return top(FunctionDef.col(col), k);
    }

    public static ColumnDef top(ColumnDef columnDef, int k) {
        return functionDef(new TdMyFunctionStatement("TOP"), columnDef, FunctionDef.col(k));
    }

    public static ColumnDef unique(String col) {
        return unique(FunctionDef.col(col));
    }

    public static ColumnDef unique(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("UNIQUE"), columnDef);
    }

    public static ColumnDef csum(String col) {
        return csum(FunctionDef.col(col));
    }

    public static ColumnDef csum(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("CSUM"), columnDef);
    }
}
