package com.dream.test;

import com.dream.boot.bean.ConfigurationBean;
import com.dream.drive.listener.DebugListener;
import com.dream.mate.transform.inject.InterceptTransformHandler;
import com.dream.mate.transform.inject.TransformInject;
import com.dream.system.cache.Cache;
import com.dream.system.cache.CacheFactory;
import com.dream.system.cache.DefaultCacheFactory;
import com.dream.system.core.listener.Listener;
import com.dream.system.inject.Inject;
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
//    public Invoker invoker() {
//        return new BlockInvoker("META-INF/keyword.txt");
//    }

    @Bean
    public Inject[] injects() {
        return new Inject[]{new TransformInject(new InterceptTransformHandler("META-INF/keyword.txt"))};
    }

    @Bean
    public ConfigurationBean configurationBean() {
        ConfigurationBean configurationBean = new ConfigurationBean(Arrays.asList("com.dream.test.base"), Arrays.asList("com.dream.test.base"));
        return configurationBean;
    }
}
