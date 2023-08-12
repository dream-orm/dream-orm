package com.dream.test;

import com.dream.boot.bean.ConfigurationBean;
import com.dream.drive.listener.DebugListener;
import com.dream.flex.annotation.FlexAPT;
import com.dream.flex.mapper.FlexMapper;
import com.dream.mate.permission.inject.PermissionHandler;
import com.dream.mate.permission.inject.PermissionInject;
import com.dream.mate.tenant.inject.TenantInject;
import com.dream.system.cache.Cache;
import com.dream.system.cache.CacheFactory;
import com.dream.system.cache.DefaultCacheFactory;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.listener.Listener;
import com.dream.system.inject.Inject;
import com.dream.system.table.TableInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@FlexAPT
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

    @Bean
    public Inject[] injects() {
        Inject[] injects = {new TenantInject(() -> 2), new PermissionInject(new PermissionHandler() {
            @Override
            public boolean isPermissionInject(MethodInfo methodInfo, TableInfo tableInfo) {
                return tableInfo.getFieldName("dept_id") != null;
            }

            @Override
            public String getPermission(String alias) {
                return alias + ".dept_id=1";
            }
        })};
        for (Inject inject : injects) {
            FlexMapper.WHITE_SET.add(inject.getClass());
        }
        return injects;
    }

    @Bean
    public ConfigurationBean configurationBean() {
        ConfigurationBean configurationBean = new ConfigurationBean(Arrays.asList("com.dream.test"), Arrays.asList("com.dream.test"));
        return configurationBean;
    }
}
