package com.dream.test.base.view;

import com.dream.system.annotation.View;
import com.dream.test.base.table.Blog;

@View(Blog.class)
public class BlogView {
    private Integer id;
    private String name;

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
}
