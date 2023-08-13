package com.dream.jdbctest;

import com.dream.boot.bean.ConfigurationBean;
import com.dream.drive.listener.DebugListener;
import com.dream.system.cache.Cache;
import com.dream.system.cache.CacheFactory;
import com.dream.system.cache.DefaultCacheFactory;
import com.dream.system.cache.MemoryCache;
import com.dream.system.core.listener.Listener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

//    @Bean
//    public CacheFactory cacheFactory() {
//        return new DefaultCacheFactory() {
//            @Override
//            public Cache getCache() {
//                return new MemoryCache();
//            }
//        };
//    }

    @Bean
    public Listener[] listeners() {
        return new Listener[]{new DebugListener()};
    }

    @Bean
    public ConfigurationBean configurationBean() {
        ConfigurationBean configurationBean = new ConfigurationBean(Arrays.asList("com.dream.jdbctest"), Arrays.asList("com.dream.jdbctest"));
        return configurationBean;
    }
}
