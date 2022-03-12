package com.moxa.dream.test.core.table;


import com.moxa.dream.module.annotation.Column;
import com.moxa.dream.module.annotation.Table;

@Table
public class Dept {
    @Column
    private int id;
    @Column
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
