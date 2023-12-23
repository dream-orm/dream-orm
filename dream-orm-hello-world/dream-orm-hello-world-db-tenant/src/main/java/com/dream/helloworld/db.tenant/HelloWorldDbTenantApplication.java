package com.dream.helloworld.db.tenant;

import com.dream.flex.annotation.FlexAPT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@FlexAPT
@SpringBootApplication
public class HelloWorldDbTenantApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloWorldDbTenantApplication.class, args);
    }

    @Bean
    public DataSource dataSource() {
        return new TenantShareDatasource();
    }
}
