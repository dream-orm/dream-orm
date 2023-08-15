package com.dream.helloworld;

import com.dream.antlr.sql.ToMYSQL;
import com.dream.antlr.sql.ToSQL;
import com.dream.boot.bean.ConfigurationBean;
import com.dream.drive.listener.DebugListener;
import com.dream.flex.annotation.FlexAPT;
import com.dream.system.core.listener.Listener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@FlexAPT
@SpringBootApplication
public class HelloWorldApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldApplication.class, args);
    }

    /**
     * 配置SQL输出
     *
     * @return
     */
    @Bean
    public Listener[] listeners() {
        return new Listener[]{new DebugListener()};
    }

    /**
     * 默认使用MySQL方言
     *
     * @return
     */
    @Bean
    public ToSQL toSQL() {
        return new ToMYSQL();
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
}
