package com.moxa.dream.driver.page;

import java.util.Collection;

public class Page<E> {
    private final int pageNum;
    private final int pageSize;
    private final long startRow;
    private long total;
    private Collection<E> rows;
    private boolean count = true;

    public Page() {
        this(1, Integer.MAX_VALUE);
    }

    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startRow = (pageNum - 1) * pageSize;
    }

    public boolean isCount() {
        return count;
    }

    public void setCount(boolean count) {
        this.count = count;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Collection<E> getRows() {
        return rows;
    }

    public void setRows(Collection<E> rows) {
        this.rows = rows;
    }

    public long getStartRow() {
        return startRow;
    }
}
