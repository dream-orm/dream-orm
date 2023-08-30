package com.dream.tdhelloworld;

import com.dream.boot.bean.ConfigurationBean;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.resultsethandler.SimpleResultSetHandler;
import com.dream.system.core.session.Session;
import com.dream.tdengine.mapper.DefaultFlexTdMapper;
import com.dream.tdengine.mapper.FlexTdMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TdHelloWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(TdHelloWorldApplication.class, args);
    }

    /**
     * 配置扫描的table和mapper路径
     *
     * @return
     */
    @Bean
    public ConfigurationBean configurationBean() {
        String packageName = this.getClass().getPackage().getName();
        List<String> pathList = Arrays.asList(packageName);
        ConfigurationBean configurationBean = new ConfigurationBean(pathList, pathList);
        return configurationBean;
    }

    @Bean
    public FlexTdMapper flexTdMapper(Session session) {
        return new DefaultFlexTdMapper(session);
    }

    @Bean
    public ResultSetHandler resultSetHandler() {
        return new SimpleResultSetHandler();
    }
}
