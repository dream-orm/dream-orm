package com.dream.boot.autoconfigure;

import com.dream.boot.bean.ConfigurationBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
@ConditionalOnSingleCandidate(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class DreamConfigurationBean {

    /**
     * 配置类创建
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public ConfigurationBean configurationBean() {
        return new ConfigurationBean();
    }
}
