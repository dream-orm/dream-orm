package com.dream.solon.test;

import com.dream.flex.mapper.FlexMapper;
import com.dream.solon.test.mapper.BlogMapper;
import com.dream.solon.test.mapper.UserMapper;
import com.dream.solon.test.table.Blog;
import com.dream.solon.test.table.User;
import com.dream.template.mapper.TemplateMapper;
import org.noear.solon.Solon;

import java.util.List;
import java.util.Map;

//@EnableShare(HikariDataSource.class)
public class SolonApp {
    public static void main(String[] args) {
        Solon.start(SolonApp.class, args);
        UserMapper userMapper = Solon.context().getBean(UserMapper.class);
        assert userMapper != null;
        Map byName = userMapper.findByName("");
        userMapper.findByName("");
        BlogMapper blogMapper = Solon.context().getBean(BlogMapper.class);
        assert blogMapper != null;
        List<Blog> blogs = blogMapper.selectBlogByUserId(1);
        TemplateMapper templateMapper = Solon.context().getBean(TemplateMapper.class);
        assert templateMapper != null;
        FlexMapper flexMapper = Solon.context().getBean(FlexMapper.class);
        assert flexMapper != null;
        List<User> userList = templateMapper.selectList(User.class, null);
        System.out.println("");
    }
}
