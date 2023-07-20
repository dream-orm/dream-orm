package com.moxa.dream.flex.test;

import com.moxa.dream.boot.bean.ConfigurationBean;
import com.moxa.dream.drive.listener.DebugListener;
import com.moxa.dream.flex.mapper.FlexMapper;
import com.moxa.dream.mate.permission.inject.PermissionHandler;
import com.moxa.dream.mate.permission.inject.PermissionInject;
import com.moxa.dream.mate.tenant.inject.TenantInject;
import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.CacheFactory;
import com.moxa.dream.system.cache.DefaultCacheFactory;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.listener.Listener;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.table.TableInfo;
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

    @Bean
    public Inject[] injects() {
        Inject[] injects = {new TenantInject(() -> 2), new PermissionInject(new PermissionHandler() {
            @Override
            public boolean isPermissionInject(MethodInfo methodInfo, TableInfo tableInfo) {
                return tableInfo.getFieldName("dept_id") != null;
            }

            @Override
            public String getPermission(MethodInfo methodInfo, TableInfo tableInfo, String alias) {
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
        ConfigurationBean configurationBean = new ConfigurationBean(Arrays.asList("com.moxa.dream.flex.test"), Arrays.asList("com.moxa.dream.flex.test"));
        return configurationBean;
    }
}
