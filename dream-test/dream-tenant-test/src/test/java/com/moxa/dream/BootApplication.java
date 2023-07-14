package com.moxa.dream;

import com.moxa.dream.boot.bean.ConfigurationBean;
import com.moxa.dream.drive.listener.DebugListener;
import com.moxa.dream.mate.tenant.inject.TenantHandler;
import com.moxa.dream.mate.tenant.inject.TenantInject;
import com.moxa.dream.mate.tenant.interceptor.TenantInterceptor;
import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.CacheFactory;
import com.moxa.dream.system.cache.DefaultCacheFactory;
import com.moxa.dream.system.core.listener.Listener;
import com.moxa.dream.system.inject.Inject;
import com.moxa.dream.system.plugin.interceptor.Interceptor;
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
    public TenantHandler tenantHandler() {
        return () -> 1;
    }

//    @Bean
//    public Invoker invoker() {
//        return new TenantInvoker();
//    }

    @Bean
    public Inject[] injects(TenantHandler tenantHandler) {
        return new Inject[]{new TenantInject(tenantHandler)};
    }

    @Bean
    public Interceptor[] interceptors(TenantHandler tenantHandler) {
        return new Interceptor[]{new TenantInterceptor(tenantHandler)};
    }

    @Bean
    public ConfigurationBean configurationBean() {
        ConfigurationBean configurationBean = new ConfigurationBean(Arrays.asList("com.moxa.dream.base"), Arrays.asList("com.moxa.dream.base"));
        return configurationBean;
    }
}
