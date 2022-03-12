package com.moxa.dream.test.core.table;

import com.moxa.dream.module.hold.annotation.Column;
import com.moxa.dream.module.hold.annotation.Table;

@Table
public class UserDept {
    @Column
    private int id;
    @Column
    private int userId;
    @Column
    private int deptId;
}
