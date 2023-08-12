package com.dream.test.base.mapper;

import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.Sql;
import com.dream.test.base.provider.BlogMapperProvider;
import com.dream.test.base.table.Blog;
import com.dream.test.base.view.UserView;

import java.util.List;

@Mapper(BlogMapperProvider.class)
public interface BlogMapper {
    @Sql("select @*() from blog where user_id=@?(userId)")
    List<Blog> selectBlogByUserId(Integer userId);

    List<Blog> selectBlogByUserId2(Integer userId);

    default List<Blog> selectBlogByUser(UserView userView) {
        return selectBlogByUserId(userView.getId());
    }

    @Sql("select @*() from blog where user_id=@?(userId) limit 1 for update")
    Blog selectForUpdate(Integer userId);
}
