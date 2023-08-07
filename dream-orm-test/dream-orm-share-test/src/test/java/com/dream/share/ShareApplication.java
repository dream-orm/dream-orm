package com.dream.share;

import com.dream.boot.bean.ConfigurationBean;
import com.dream.boot.share.EnableShare;
import com.dream.drive.listener.DebugListener;
import com.dream.mate.share.datasource.ShareDataSource;
import com.dream.system.cache.Cache;
import com.dream.system.cache.CacheFactory;
import com.dream.system.cache.DefaultCacheFactory;
import com.dream.system.core.listener.Listener;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableShare(HikariDataSource.class)
public class ShareApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShareApplication.class, args);
    }

    @Bean
    public CacheFactory cacheFactory() {
        return new DefaultCacheFactory() {
            @Override
            public Cache getCache() {
                return null;
            }
        };
    }

    @Bean
    public Listener[] listeners() {
        return new Listener[]{new DebugListener()};
    }

    //    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("BMW#Halu@1234%");
        dataSource.setJdbcUrl("jdbc:mysql://192.168.0.3/d-open?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useAffectedRows=true&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai");
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setUsername("root");
        dataSource1.setPassword("BMW#Halu@1234%");
        dataSource1.setJdbcUrl("jdbc:mysql://192.168.0.3/d-open-6c?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useAffectedRows=true&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai");
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", dataSource);
        dataSourceMap.put("slave", dataSource1);
        return new ShareDataSource(dataSourceMap);
    }

    @Bean
    public ConfigurationBean configurationBean() {
        ConfigurationBean configurationBean = new ConfigurationBean(Arrays.asList("com.dream.share"), Arrays.asList("com.dream.share"));
        return configurationBean;
    }
}
