package com.dream.solon.test;

import com.dream.antlr.sql.ToMYSQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.solon.bean.ConfigurationBean;
import com.zaxxer.hikari.HikariDataSource;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class Config {
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
