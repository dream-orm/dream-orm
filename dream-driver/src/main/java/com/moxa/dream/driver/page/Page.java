package com.moxa.dream.driver.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Page<E> extends ArrayList<E> implements Serializable {
    public static final long serialVersionUID = 42L;
    private long total;
    private int pageNum;
    private int pageSize;
    private Collection<E> row;
    private long startRow;
    private boolean count = true;

    public Page() {
        this(1, Integer.MAX_VALUE);
    }

    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startRow = (pageNum - 1) * pageSize;
    }

    public static Page of(int pageNum, int pageSize) {
        return new Page(pageNum, pageSize);
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


    public Collection<E> getRow() {
        return row;
    }

    public void setRow(Collection<E> row) {
        this.row = row;
    }

    public long getStartRow() {
        return startRow;
    }

    @Override
    public String toString() {
        return "Page{" +
                "total=" + total +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", count=" + count +
                ", row=" + row +
                '}';
    }
}
