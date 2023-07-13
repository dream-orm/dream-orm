package com.moxa.dream.flex.test;

import com.moxa.dream.flex.test.table.User;
import com.moxa.dream.system.annotation.Mapper;
import com.moxa.dream.system.annotation.PageQuery;
import com.moxa.dream.system.annotation.Param;
import com.moxa.dream.system.annotation.Sql;
import com.moxa.dream.system.config.Page;

import java.util.List;

@Mapper
public interface HelloMapper {
    @PageQuery
    @Sql("select name from user")
    public List<User> selectPage(@Param("page") Page page);
}
