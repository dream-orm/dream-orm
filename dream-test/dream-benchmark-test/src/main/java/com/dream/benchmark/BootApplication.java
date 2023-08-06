package com.dream.benchmark;

import com.dream.boot.bean.ConfigurationBean;
import com.dream.system.cache.CacheFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class BootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    public CacheFactory cacheFactory() {
        return () -> null;
    }

//    @Bean
//    public Listener listeners() {
//        return new DebugListener();
//    }

    @Bean
    public ConfigurationBean configurationBean() {
        ConfigurationBean configurationBean = new ConfigurationBean(Arrays.asList("com.dream.benchmark"), Arrays.asList("com.dream.benchmark"));
        return configurationBean;
    }

}
