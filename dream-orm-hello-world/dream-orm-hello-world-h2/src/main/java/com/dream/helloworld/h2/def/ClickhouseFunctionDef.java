package com.dream.helloworld.h2.def;

import com.dream.antlr.smt.DefaultMyFunctionStatement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;

import static com.dream.flex.def.FunctionDef.col;
import static com.dream.flex.def.FunctionDef.functionDef;

public class ClickhouseFunctionDef {
    public static ColumnDef column2(String table,String column) {
        return new ColumnDef(FunctionDef.table(table),column);
    }
    public static ColumnDef toTypeName(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toTypeName"), columnDef);
    }

    public static ColumnDef plus(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("plus"), columnDef, columnDef2);
    }

    public static ColumnDef minus(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("minus"), columnDef, columnDef2);
    }

    public static ColumnDef multiply(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("multiply"), columnDef, columnDef2);
    }

    public static ColumnDef divide(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("divide"), columnDef, columnDef2);
    }

    public static ColumnDef intDiv(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("intDiv"), columnDef, columnDef2);
    }

    public static ColumnDef intDivOrZero(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("intDivOrZero"), columnDef, columnDef2);
    }

    public static ColumnDef modulo(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("modulo"), columnDef, columnDef2);
    }

    public static ColumnDef moduloOrZero(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("moduloOrZero"), columnDef, columnDef2);
    }

    public static ColumnDef negate(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("negate"), columnDef);
    }

    public static ColumnDef gcd(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("gcd"), columnDef, columnDef2);
    }

    public static ColumnDef lcm(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("lcm"), columnDef, columnDef2);
    }

    public static ColumnDef max2(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("max2"), columnDef, columnDef2);
    }

    public static ColumnDef min2(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new DefaultMyFunctionStatement("min2"), columnDef, columnDef2);
    }

    public static ColumnDef toInt8(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt8"), columnDef);
    }

    public static ColumnDef toInt16(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt16"), columnDef);
    }

    public static ColumnDef toInt32(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt32"), columnDef);
    }

    public static ColumnDef toInt64(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt64"), columnDef);
    }

    public static ColumnDef toInt8OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt8OrZero"), columnDef);
    }

    public static ColumnDef toInt16OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt16OrZero"), columnDef);
    }

    public static ColumnDef toInt32OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt32OrZero"), columnDef);
    }

    public static ColumnDef toInt64OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt64OrZero"), columnDef);
    }

    public static ColumnDef toInt8OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt8OrNull"), columnDef);
    }

    public static ColumnDef toInt16OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt16OrNull"), columnDef);
    }

    public static ColumnDef toInt32OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt32OrNull"), columnDef);
    }

    public static ColumnDef toInt64OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toInt64OrNull"), columnDef);
    }

    public static ColumnDef toUInt8(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt8"), columnDef);
    }

    public static ColumnDef toUInt16(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt16"), columnDef);
    }

    public static ColumnDef toUInt32(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt32"), columnDef);
    }

    public static ColumnDef toUInt64(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt64"), columnDef);
    }

    public static ColumnDef toUInt8OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt8OrZero"), columnDef);
    }

    public static ColumnDef toUInt16OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt16OrZero"), columnDef);
    }

    public static ColumnDef toUInt32OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt32OrZero"), columnDef);
    }

    public static ColumnDef toUInt64OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt64OrZero"), columnDef);
    }

    public static ColumnDef toUInt8OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt8OrNull"), columnDef);
    }

    public static ColumnDef toUInt16OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt16OrNull"), columnDef);
    }

    public static ColumnDef toUInt32OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt32OrNull"), columnDef);
    }

    public static ColumnDef toUInt64OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toUInt64OrNull"), columnDef);
    }

    public static ColumnDef toFloat32(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toFloat32"), columnDef);
    }

    public static ColumnDef toFloat64(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toFloat64"), columnDef);
    }

    public static ColumnDef toFloat32OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toFloat32OrZero"), columnDef);
    }

    public static ColumnDef toFloat64OrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toFloat64OrZero"), columnDef);
    }

    public static ColumnDef toFloat32OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toFloat32OrNull"), columnDef);
    }

    public static ColumnDef toFloat64OrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toFloat64OrNull"), columnDef);
    }

    public static ColumnDef toDate(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toDate"), columnDef);
    }

    public static ColumnDef toDateOrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toDateOrZero"), columnDef);
    }

    public static ColumnDef toDateOrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toDateOrNull"), columnDef);
    }

    public static ColumnDef toDateTime(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toDateTime"), columnDef);
    }

    public static ColumnDef toDateTimeOrZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toDateTimeOrZero"), columnDef);
    }

    public static ColumnDef toDateTimeOrNull(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toDateTimeOrNull"), columnDef);
    }

    public static ColumnDef toDecimal32(ColumnDef columnDef, int s) {
        return functionDef(new DefaultMyFunctionStatement("toDecimal32"), columnDef, col(s));
    }

    public static ColumnDef toDecimal64(ColumnDef columnDef, int s) {
        return functionDef(new DefaultMyFunctionStatement("toDecimal64"), columnDef, col(s));
    }

    public static ColumnDef toDecimal128(ColumnDef columnDef, int s) {
        return functionDef(new DefaultMyFunctionStatement("toDecimal128"), columnDef, col(s));
    }

    public static ColumnDef toDecimal32OrNull(ColumnDef columnDef, int s) {
        return functionDef(new DefaultMyFunctionStatement("toDecimal32OrNull"), columnDef, col(s));
    }

    public static ColumnDef toDecimal64OrNull(ColumnDef columnDef, int s) {
        return functionDef(new DefaultMyFunctionStatement("toDecimal64OrNull"), columnDef, col(s));
    }

    public static ColumnDef toDecimal128OrNull(ColumnDef columnDef, int s) {
        return functionDef(new DefaultMyFunctionStatement("toDecimal128OrNull"), columnDef, col(s));
    }

    public static ColumnDef toDecimal32OrZero(ColumnDef columnDef, int s) {
        return functionDef(new DefaultMyFunctionStatement("toDecimal32OrZero"), columnDef, col(s));
    }

    public static ColumnDef toDecimal64OrZero(ColumnDef columnDef, int s) {
        return functionDef(new DefaultMyFunctionStatement("toDecimal64OrZero"), columnDef, col(s));
    }

    public static ColumnDef toDecimal128OrZero(ColumnDef columnDef, int s) {
        return functionDef(new DefaultMyFunctionStatement("toDecimal128OrZero"), columnDef, col(s));
    }

    public static ColumnDef toString(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toString"), columnDef);
    }

    public static ColumnDef toFixedString(ColumnDef columnDef, int n) {
        return functionDef(new DefaultMyFunctionStatement("toFixedString"), columnDef, col(n));
    }

    public static ColumnDef toStringCutToZero(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toStringCutToZero"), columnDef);
    }

    public static ColumnDef reinterpretAsUInt8(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsUInt8"), columnDef);
    }

    public static ColumnDef reinterpretAsUInt16(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsUInt16"), columnDef);
    }

    public static ColumnDef reinterpretAsUInt32(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsUInt32"), columnDef);
    }

    public static ColumnDef reinterpretAsUInt64(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsUInt64"), columnDef);
    }

    public static ColumnDef reinterpretAsInt8(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsInt8"), columnDef);
    }

    public static ColumnDef reinterpretAsInt16(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsInt16"), columnDef);
    }

    public static ColumnDef reinterpretAsInt32(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsInt32"), columnDef);
    }

    public static ColumnDef reinterpretAsInt64(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsInt64"), columnDef);
    }

    public static ColumnDef reinterpretAsFloat32(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsFloat32"), columnDef);
    }

    public static ColumnDef reinterpretAsFloat64(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsFloat64"), columnDef);
    }

    public static ColumnDef reinterpretAsDate(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsDate"), columnDef);
    }

    public static ColumnDef reinterpretAsDateTime(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsDateTime"), columnDef);
    }

    public static ColumnDef reinterpretAsString(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsString"), columnDef);
    }

    public static ColumnDef reinterpretAsFixedString(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("reinterpretAsFixedString"), columnDef);
    }

    public static ColumnDef toIntervalSecond(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toIntervalSecond"), columnDef);
    }

    public static ColumnDef toIntervalMinute(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toIntervalMinute"), columnDef);
    }

    public static ColumnDef toIntervalHour(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toIntervalHour"), columnDef);
    }

    public static ColumnDef toIntervalDay(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toIntervalDay"), columnDef);
    }

    public static ColumnDef toIntervalWeek(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toIntervalWeek"), columnDef);
    }

    public static ColumnDef toIntervalMonth(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toIntervalMonth"), columnDef);
    }

    public static ColumnDef toIntervalQuarter(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toIntervalQuarter"), columnDef);
    }

    public static ColumnDef toIntervalYear(ColumnDef columnDef) {
        return functionDef(new DefaultMyFunctionStatement("toIntervalYear"), columnDef);
    }
}
