package org.example.dreamormhellospringboot.config;

import com.dream.boot.bean.ConfigurationBean;
import com.dream.template.sequence.Sequence;
import com.zaxxer.hikari.HikariDataSource;
import org.example.dreamormhellospringboot.sequence.SnowFlakeSequence;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;


@Configuration
public class DreamConfig {

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource dataSource() {
        return new HikariDataSource();
    }


    /**
     * 定义主键序列
     *
     * @return
     */
    @Bean
    public Sequence sequence() {
        return new SnowFlakeSequence();
    }

    /**
     * 配置扫码哦配置
     *
     * @return
     */
    @Bean
    public ConfigurationBean configurationBean() {
        List<String> packages = Arrays.asList("org.example.dreamormhellospringboot");
        return new ConfigurationBean(packages, packages);
    }

//    /**
//     * 注入多租户，逻辑删除等
//     * @return
//     */
//    @Bean
//    public Inject[] injects() {
//        return new Inject[0];
//    }
}
