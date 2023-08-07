package com.dream.test;

import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.PageQuery;
import com.dream.system.annotation.Param;
import com.dream.system.annotation.Sql;
import com.dream.system.config.Page;
import com.dream.test.table.User;

import java.util.List;

@Mapper
public interface HelloMapper {
    @PageQuery
    @Sql("select name from user")
    public List<User> selectPage(@Param("page") Page page);
}
