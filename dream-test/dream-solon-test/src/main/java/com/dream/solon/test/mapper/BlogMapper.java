package com.dream.solon.test.mapper;

import com.dream.solon.test.provider.BlogMapperProvider;
import com.dream.solon.test.table.Blog;
import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.Sql;

import java.util.List;

@Mapper(BlogMapperProvider.class)
public interface BlogMapper {
    @Sql("select @*() from blog where user_id=@?(userId)")
    List<Blog> selectBlogByUserId(Integer userId);

    List<Blog> selectBlogByUserId2(Integer userId);

    @Sql("select @*() from blog where user_id=@?(userId) limit 1 for update")
    Blog selectForUpdate(Integer userId);
}
