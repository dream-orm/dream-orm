package com.dream.helloworld.solon;

import com.dream.flex.mapper.FlexMapper;
import com.dream.helloworld.solon.mapper.BlogMapper;
import com.dream.helloworld.solon.mapper.UserMapper;
import com.dream.template.mapper.TemplateMapper;
import org.noear.solon.Solon;

//@EnableShare(HikariDataSource.class)
public class SolonApp {
    public static void main(String[] args) {
        Solon.start(SolonApp.class, args);
        UserMapper userMapper = Solon.context().getBean(UserMapper.class);
        assert userMapper != null;
        BlogMapper blogMapper = Solon.context().getBean(BlogMapper.class);
        assert blogMapper != null;
        TemplateMapper templateMapper = Solon.context().getBean(TemplateMapper.class);
        assert templateMapper != null;
        FlexMapper flexMapper = Solon.context().getBean(FlexMapper.class);
        assert flexMapper != null;
        System.exit(0);
    }
}
