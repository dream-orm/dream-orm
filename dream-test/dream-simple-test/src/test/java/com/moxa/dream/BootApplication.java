package com.moxa.dream;

import com.moxa.dream.boot.bean.ConfigurationBean;
import com.moxa.dream.drive.listener.DebugListener;
import com.moxa.dream.system.cache.CacheFactory;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.listener.Listener;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.template.sequence.Sequence;
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

    @Bean
    public Listener listeners() {
        return new DebugListener();
    }

    @Bean
    public ConfigurationBean configurationBean() {
        ConfigurationBean configurationBean = new ConfigurationBean(Arrays.asList("com.moxa.dream.base"), Arrays.asList("com.moxa.dream.base"));
        return configurationBean;
    }

    @Bean
    public Sequence sequence() {
        return new Sequence() {
            @Override
            public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg) {

            }
        };
    }

}
