package com.moxa.dream.solon.test;

import com.moxa.dream.flex.mapper.FlexMapper;
import com.moxa.dream.solon.share.EnableShare;
import com.moxa.dream.solon.test.mapper.BlogMapper;
import com.moxa.dream.solon.test.mapper.UserMapper;
import com.moxa.dream.template.mapper.TemplateMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.Solon;
//@EnableShare(HikariDataSource.class)
public class SolonApp {
    public static void main(String[] args) {
        Solon.start(SolonApp.class, args);
        UserMapper userMapper = Solon.context().getBean(UserMapper.class);
        assert userMapper!=null;
        BlogMapper blogMapper = Solon.context().getBean(BlogMapper.class);
        assert blogMapper!=null;
        TemplateMapper templateMapper = Solon.context().getBean(TemplateMapper.class);
        assert templateMapper!=null;
        FlexMapper flexMapper = Solon.context().getBean(FlexMapper.class);
        assert flexMapper!=null;
    }
}
