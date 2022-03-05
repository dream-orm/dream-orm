package com.moxa.dream.test.core.view;

import com.moxa.dream.module.annotation.View;

import java.sql.Timestamp;

@View("user")
public class MyViewUser {
    private int id;
    private String name;
    private Timestamp createTime;
}
