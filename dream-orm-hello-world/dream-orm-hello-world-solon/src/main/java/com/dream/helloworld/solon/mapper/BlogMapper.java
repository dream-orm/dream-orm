package com.dream.helloworld.solon.mapper;

import com.dream.helloworld.solon.table.Blog;
import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.Sql;

import java.util.List;

@Mapper
public interface BlogMapper {
    @Sql("select @*() from blog where user_id=@?(userId)")
    List<Blog> selectBlogByUserId(Integer userId);

    @Sql("select @*() from blog where user_id=@?(userId) limit 1 for update")
    Blog selectForUpdate(Integer userId);
}
