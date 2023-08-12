package com.dream.test;

import com.dream.boot.bean.ConfigurationBean;
import com.dream.drive.listener.DebugListener;
import com.dream.system.cache.Cache;
import com.dream.system.cache.CacheFactory;
import com.dream.system.cache.DefaultCacheFactory;
import com.dream.system.core.listener.Listener;
import com.dream.system.table.ColumnInfo;
import com.dream.template.sequence.AbstractSequence;
import com.dream.template.sequence.Sequence;
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
//    public Invoker[] invokers() {
//        return new Invoker[]{new PermissionInjectInvoker(), new PermissionGetInvoker()};
//    }
    @Bean
    public Sequence sequence() {
        return new AbstractSequence() {
            @Override
            protected Object sequence(ColumnInfo columnInfo) {
                String name = columnInfo.getName();
                if (name.equals("id")) {
                    return 100l;
                } else if (name.equals("id2")) {
                    return 101l;
                }
                return null;
            }
        };
    }

    @Bean
    public ConfigurationBean configurationBean() {
        ConfigurationBean configurationBean = new ConfigurationBean(Arrays.asList("com.dream.test.base"), Arrays.asList("com.dream.test.base"));
        return configurationBean;
    }
}
