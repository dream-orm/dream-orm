package com.dream.test.view;

import com.dream.system.annotation.View;
import com.dream.test.table.Blog;

import java.util.Date;

@View(Blog.class)
public class BlogView {
    private Integer id;
    private String name;
    private Date publishTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
