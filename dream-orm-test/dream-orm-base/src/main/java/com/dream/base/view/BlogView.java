package com.dream.base.view;

import com.dream.base.table.Blog;
import com.dream.system.annotation.View;

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
