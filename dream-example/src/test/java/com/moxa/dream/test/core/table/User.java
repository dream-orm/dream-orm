package com.moxa.dream.test.core.table;

import com.moxa.dream.module.hold.annotation.Column;
import com.moxa.dream.module.hold.annotation.Id;
import com.moxa.dream.module.hold.annotation.Table;

import java.sql.Timestamp;

@Table("user")
public class User {
    @Column
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private Timestamp createTime;

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

    public Timestamp getCreateTime() {
        return createTime;
    }
}
