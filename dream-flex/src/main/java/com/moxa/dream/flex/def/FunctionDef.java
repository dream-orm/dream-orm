package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.FunctionStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.SymbolStatement;

public class FunctionDef {
    public static ColumnDef abs(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AbsStatement(), columnDef);
    }

    public static ColumnDef ceil(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CeilStatement(), columnDef);
    }

    public static ColumnDef floor(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.FloorStatement(), columnDef);
    }

    public static ColumnDef sign(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SignStatement(), columnDef);
    }

    public static ColumnDef truncate(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.TruncateStatement(), columnDef);
    }

    public static ColumnDef round(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.RoundStatement(), columnDef);
    }

    public static ColumnDef power(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.PowerStatement(), columnDef, columnDef2);
    }

    public static ColumnDef power(ColumnDef columnDef, int column2) {
        return functionDef(new FunctionStatement.PowerStatement(), columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(column2))));
    }

    public static ColumnDef sqrt(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SqrtStatement(), columnDef);
    }

    public static ColumnDef exp(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.ExpStatement(), columnDef);
    }

    public static ColumnDef mod(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new FunctionStatement.ModStatement(), columnDef, columnDef2);
    }

    public static ColumnDef mod(ColumnDef columnDef, int column2) {
        return functionDef(new FunctionStatement.ModStatement(), columnDef, new ColumnDef(new SymbolStatement.LetterStatement(String.valueOf(column2))));
    }

    public static ColumnDef log(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LogStatement(), columnDef);
    }

    public static ColumnDef log2(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.Log2Statement(), columnDef);
    }

    public static ColumnDef log10(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.Log10Statement(), columnDef);
    }

    public static ColumnDef sin(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.SinStatement(), columnDef);
    }

    public static ColumnDef asin(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AsinStatement(), columnDef);
    }

    public static ColumnDef cos(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CosStatement(), columnDef);
    }

    public static ColumnDef acos(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AcosStatement(), columnDef);
    }

    public static ColumnDef tan(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.TanStatement(), columnDef);
    }

    public static ColumnDef atan(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.AtanStatement(), columnDef);
    }

    public static ColumnDef cot(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.CotStatement(), columnDef);
    }

    public static ColumnDef len(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LenStatement(), columnDef);
    }

    public static ColumnDef length(ColumnDef columnDef) {
        return functionDef(new FunctionStatement.LengthStatement(), columnDef);
    }

    public static ColumnDef concat(ColumnDef... columnDefs) {
        return functionDef(new FunctionStatement.ConcatStatement(), columnDefs);
    }

    public static SelectDef select(ColumnDef... columnDefs) {
        return new QueryDef().select(columnDefs);
    }

    private static ColumnDef functionDef(FunctionStatement functionStatement, ColumnDef... columnDefs) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        for (ColumnDef columnDef : columnDefs) {
            listColumnStatement.add(columnDef.statement);
        }
        functionStatement.setParamsStatement(listColumnStatement);
        return new ColumnDef(functionStatement);
    }
}
