package com.moxa.dream.test.core.table;

import com.moxa.dream.module.annotation.Column;
import com.moxa.dream.module.annotation.Table;

@Table
public class UserDept {
    @Column
    private int id;
    @Column
    private int userId;
    @Column
    private int deptId;
}
