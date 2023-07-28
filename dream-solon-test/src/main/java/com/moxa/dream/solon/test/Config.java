package com.moxa.dream.solon.test;

import com.moxa.dream.antlr.sql.ToMYSQL;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.flex.mapper.FlexMapper;
import com.moxa.dream.solon.bean.ConfigurationBean;
import com.moxa.dream.solon.test.mapper.UserMapper;
import com.moxa.dream.template.mapper.TemplateMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Config {
    @Inject
    private TemplateMapper templateMapper;
    @Inject
    private FlexMapper flexMapper;
    @Inject
    private UserMapper userMapper;
    @Bean(name = "db1", typed = true)
    public DataSource db1(@Inject("${test.db1}") HikariDataSource ds) {
        return ds;
    }

    @Bean
    public ConfigurationBean configurationBean() {
        String packageName = this.getClass().getPackage().getName();
        List<String> list = Arrays.asList(packageName);
        return new ConfigurationBean(list, list);
    }

    @Bean
    public ToSQL toSQL() {
        return new ToMYSQL();
    }
}
