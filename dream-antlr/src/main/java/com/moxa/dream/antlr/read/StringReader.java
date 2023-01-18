package com.moxa.dream.antlr.read;

public class StringReader {
    private final int length;
    protected String value;
    private int next = 0;
    private int mark = 0;
    private int start = 0;
    private int end = 0;
    private boolean close = false;

    public StringReader(String value) {
        this.value = value == null ? "" : value;
        this.length = this.value.length();
    }

    protected int read() {
        if (next >= length) {
            return -1;
        }
        start = next;
        int c = value.charAt(next++);
        end = next;
        return c;
    }

    protected int read(char[] cbuf, int off, int len) {
        value.getChars(next, next + len, cbuf, off);
        start = next;
        next += len;
        end = next;
        return len;
    }

    protected int skip(int ns) {
        start = next;
        next += ns;
        end = next;
        return ns;
    }

    protected void mark() {
        mark = next;

    }

    protected int value() {
        if (next >= length) {
            return -1;
        }
        return value.charAt(next);
    }

    protected void reset() {
        next = mark;
    }

    public void close() {
        close = true;
    }

    public boolean isClose() {
        return close;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

}
