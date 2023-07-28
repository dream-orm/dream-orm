package demo;

import com.moxa.dream.solon.bean.ConfigurationBean;
import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class Config {
    @Bean
    public ConfigurationBean configurationBean() {
        return new ConfigurationBean(Arrays.asList("demo"), Arrays.asList("demo"));
    }

    @Bean("db1")
    public DataSource db1(@Inject("dataSource.db1") DataSource dataSource) {
        return dataSource;
    }
}
