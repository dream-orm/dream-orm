package com.moxa.dream.solon.test.mapper;

import com.moxa.dream.solon.test.provider.BlogMapperProvider;
import com.moxa.dream.solon.test.table.Blog;
import com.moxa.dream.solon.test.view.UserView;
import com.moxa.dream.system.annotation.Mapper;
import com.moxa.dream.system.annotation.Sql;

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
