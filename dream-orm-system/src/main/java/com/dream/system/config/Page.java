package com.dream.system.config;

import java.util.Collection;

public class Page<E> {
    private int pageNum;
    private int pageSize;
    private long total;
    private Collection<E> rows;

    public Page() {
        this(1, Integer.MAX_VALUE);
    }

    public Page(int pageNum, int pageSize) {
        this(pageNum, pageSize, 0);
    }

    public Page(int pageNum, int pageSize, long total) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Collection<E> getRows() {
        return rows;
    }

    public void setRows(Collection<E> rows) {
        this.rows = rows;
    }

    public long getStartRow() {
        return (pageNum - 1) * pageSize;
    }
}
