package com.dream.tdengine.def;

import com.dream.antlr.smt.SymbolStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.tdengine.statement.TdMyFunctionStatement;

public class TdFunctionDef extends FunctionDef {

    public static ColumnDef database() {
        return functionDef(new TdMyFunctionStatement("DATABASE"));
    }

    public static ColumnDef client_version() {
        return functionDef(new TdMyFunctionStatement("CLIENT_VERSION"));
    }

    public static ColumnDef server_version() {
        return functionDef(new TdMyFunctionStatement("SERVER_VERSION"));
    }

    public static ColumnDef server_status() {
        return functionDef(new TdMyFunctionStatement("SERVER_STATUS"));
    }

    public static ColumnDef today() {
        return functionDef(new TdMyFunctionStatement("TODAY"));
    }

    public static ColumnDef timezone() {
        return functionDef(new TdMyFunctionStatement("TIMEZONE"));
    }

    public static ColumnDef current_user() {
        return functionDef(new TdMyFunctionStatement("CURRENT_USER"));
    }

    public static ColumnDef to_iso8601(String column) {
        return to_iso8601(col(column));
    }

    public static ColumnDef to_iso8601(String column, String timezone) {
        return to_iso8601(col(column), timezone);
    }

    public static ColumnDef to_iso8601(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("TO_ISO8601"), columnDef);
    }

    public static ColumnDef to_iso8601(ColumnDef columnDef, String timezone) {
        return functionDef(new TdMyFunctionStatement("TO_ISO8601"), columnDef, new ColumnDef(new SymbolStatement.StrStatement(timezone)));
    }

    public static ColumnDef to_json(String column) {
        return to_json(col(column));
    }

    public static ColumnDef to_json(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("TO_JSON"), columnDef);
    }

    public static ColumnDef timediff_1b(String column, ColumnDef column2) {
        return timediff(col(column), col(column2), "1b");
    }

    public static ColumnDef timediff_1u(String column, ColumnDef column2) {
        return timediff(col(column), col(column2), "1u");
    }

    public static ColumnDef timediff_1a(String column, ColumnDef column2) {
        return timediff(col(column), col(column2), "1a");
    }

    public static ColumnDef timediff_1s(String column, ColumnDef column2) {
        return timediff(col(column), col(column2), "1s");
    }

    public static ColumnDef timediff_1m(String column, ColumnDef column2) {
        return timediff(col(column), col(column2), "1m");
    }

    public static ColumnDef timediff_1h(String column, ColumnDef column2) {
        return timediff(col(column), col(column2), "1h");
    }

    public static ColumnDef timediff_1d(String column, ColumnDef column2) {
        return timediff(col(column), col(column2), "1d");
    }

    public static ColumnDef timediff_1w(String column, ColumnDef column2) {
        return timediff(col(column), col(column2), "1w");
    }

    public static ColumnDef timediff_1b(ColumnDef columnDef, ColumnDef columnDef2) {
        return timediff(columnDef, columnDef2, "1b");
    }

    public static ColumnDef timediff_1u(ColumnDef columnDef, ColumnDef columnDef2) {
        return timediff(columnDef, columnDef2, "1u");
    }

    public static ColumnDef timediff_1a(ColumnDef columnDef, ColumnDef columnDef2) {
        return timediff(columnDef, columnDef2, "1a");
    }

    public static ColumnDef timediff_1s(ColumnDef columnDef, ColumnDef columnDef2) {
        return timediff(columnDef, columnDef2, "1s");
    }

    public static ColumnDef timediff_1m(ColumnDef columnDef, ColumnDef columnDef2) {
        return timediff(columnDef, columnDef2, "1m");
    }

    public static ColumnDef timediff_1h(ColumnDef columnDef, ColumnDef columnDef2) {
        return timediff(columnDef, columnDef2, "1h");
    }

    public static ColumnDef timediff_1d(ColumnDef columnDef, ColumnDef columnDef2) {
        return timediff(columnDef, columnDef2, "1d");
    }

    public static ColumnDef timediff_1w(ColumnDef columnDef, ColumnDef columnDef2) {
        return timediff(columnDef, columnDef2, "1w");
    }

    public static ColumnDef timediff(String column, ColumnDef column2) {
        return timediff(col(column), col(column2));
    }

    public static ColumnDef timediff(ColumnDef columnDef, ColumnDef columnDef2) {
        return functionDef(new TdMyFunctionStatement("TIMEDIFF"), columnDef, columnDef2);
    }

    public static ColumnDef timediff(ColumnDef columnDef, ColumnDef columnDef2, String timeUnit) {
        return functionDef(new TdMyFunctionStatement("TIMEDIFF"), columnDef, columnDef2, col(timeUnit));
    }

    public static ColumnDef apercentile(String column, int p) {
        return apercentile(col(column), p);
    }

    public static ColumnDef apercentile(ColumnDef columnDef, int p) {
        return functionDef(new TdMyFunctionStatement("APERCENTILE"), columnDef, col(p));
    }

    public static ColumnDef apercentile(String column, int p, String algoType) {
        return apercentile(col(column), p, algoType);
    }

    public static ColumnDef apercentile_default(String column, int p) {
        return apercentile_default(col(column), p);
    }

    public static ColumnDef apercentile_tDigest(String column, int p) {
        return apercentile_tDigest(col(column), p);
    }

    public static ColumnDef apercentile_default(ColumnDef columnDef, int p) {
        return apercentile(columnDef, p, "default");
    }

    public static ColumnDef apercentile_tDigest(ColumnDef columnDef, int p) {
        return apercentile(columnDef, p, "t-digest");
    }

    public static ColumnDef apercentile(ColumnDef columnDef, int p, String algoType) {
        return functionDef(new TdMyFunctionStatement("APERCENTILE"), columnDef, col(p), new ColumnDef(new SymbolStatement.StrStatement(algoType)));
    }

    public static ColumnDef elapsed(String column, String timeUnit) {
        return elapsed(col(column), timeUnit);
    }

    public static ColumnDef elapsed_1b(String column) {
        return elapsed(col(column), "1b");
    }

    public static ColumnDef elapsed_1u(String column) {
        return elapsed(col(column), "1u");
    }

    public static ColumnDef elapsed_1a(String column) {
        return elapsed(col(column), "1a");
    }

    public static ColumnDef elapsed_1s(String column) {
        return elapsed(col(column), "1s");
    }

    public static ColumnDef elapsed_1m(String column) {
        return elapsed(col(column), "1m");
    }

    public static ColumnDef elapsed_1h(String column) {
        return elapsed(col(column), "1h");
    }

    public static ColumnDef elapsed_1d(String column) {
        return elapsed(col(column), "1d");
    }

    public static ColumnDef elapsed_1w(String column) {
        return elapsed(col(column), "1w");
    }

    public static ColumnDef elapsed_1b(ColumnDef columnDef) {
        return elapsed(columnDef, "1b");
    }

    public static ColumnDef elapsed_1u(ColumnDef columnDef) {
        return elapsed(columnDef, "1u");
    }

    public static ColumnDef elapsed_1a(ColumnDef columnDef) {
        return elapsed(columnDef, "1a");
    }

    public static ColumnDef elapsed_1s(ColumnDef columnDef) {
        return elapsed(columnDef, "1s");
    }

    public static ColumnDef elapsed_1m(ColumnDef columnDef) {
        return elapsed(columnDef, "1m");
    }

    public static ColumnDef elapsed_1h(ColumnDef columnDef) {
        return elapsed(columnDef, "1h");
    }

    public static ColumnDef elapsed_1d(ColumnDef columnDef) {
        return elapsed(columnDef, "1d");
    }

    public static ColumnDef elapsed_1w(ColumnDef columnDef) {
        return elapsed(columnDef, "1w");
    }

    public static ColumnDef elapsed(ColumnDef columnDef, String timeUnit) {
        return functionDef(new TdMyFunctionStatement("ELAPSED"), columnDef, col(timeUnit));
    }

    public static ColumnDef leastsquares(String column, double startVal, double stepVal) {
        return leastsquares(col(column), startVal, stepVal);
    }

    public static ColumnDef leastsquares(ColumnDef columnDef, double startVal, double stepVal) {
        return functionDef(new TdMyFunctionStatement("LEASTSQUARES"), columnDef, col(startVal), col(startVal));
    }

    public static ColumnDef spread(String column) {
        return spread(col(column));
    }

    public static ColumnDef spread(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("SPREAD"), columnDef);
    }

    public static ColumnDef stddev(String column) {
        return stddev(col(column));
    }

    public static ColumnDef stddev(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("STDDEV"), columnDef);
    }

    public static ColumnDef hyperloglog(String column) {
        return hyperloglog(col(column));
    }

    public static ColumnDef hyperloglog(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("HYPERLOGLOG"), columnDef);
    }

    public static ColumnDef bottom(String column, int k) {
        return bottom(col(column), k);
    }

    public static ColumnDef bottom(ColumnDef columnDef, int k) {
        return functionDef(new TdMyFunctionStatement("BOTTOM"), columnDef, col(k));
    }

    public static ColumnDef first(String col) {
        return first(col(col));
    }

    public static ColumnDef first(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("FIRST"), columnDef);
    }

    public static ColumnDef last(String col) {
        return last(col(col));
    }

    public static ColumnDef last(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("LAST"), columnDef);
    }

    public static ColumnDef last_row(String col) {
        return last_row(col(col));
    }

    public static ColumnDef last_row(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("LAST_ROW"), columnDef);
    }

    public static ColumnDef mode(String col) {
        return mode(col(col));
    }

    public static ColumnDef mode(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("LAST_ROW"), columnDef);
    }

    public static ColumnDef sample(String col, int k) {
        return sample(col(col), k);
    }

    public static ColumnDef sample(ColumnDef columnDef, int k) {
        return functionDef(new TdMyFunctionStatement("LAST_ROW"), columnDef, col(k));
    }

    public static ColumnDef tail(String col, int k) {
        return tail(col(col), k);
    }

    public static ColumnDef tail(String col, int k, Integer offset) {
        return tail(col(col), k, offset);
    }

    public static ColumnDef tail(ColumnDef columnDef, int k) {
        return functionDef(new TdMyFunctionStatement("TAIL"), columnDef, col(k));
    }

    public static ColumnDef tail(ColumnDef columnDef, int k, Integer offset) {
        return functionDef(new TdMyFunctionStatement("TAIL"), columnDef, col(k), col(offset));
    }

    public static ColumnDef top(String col, int k) {
        return top(col(col), k);
    }

    public static ColumnDef top(ColumnDef columnDef, int k) {
        return functionDef(new TdMyFunctionStatement("TOP"), columnDef, col(k));
    }

    public static ColumnDef unique(String col) {
        return unique(col(col));
    }

    public static ColumnDef unique(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("UNIQUE"), columnDef);
    }

    public static ColumnDef csum(String col) {
        return csum(col(col));
    }

    public static ColumnDef csum(ColumnDef columnDef) {
        return functionDef(new TdMyFunctionStatement("CSUM"), columnDef);
    }
}
