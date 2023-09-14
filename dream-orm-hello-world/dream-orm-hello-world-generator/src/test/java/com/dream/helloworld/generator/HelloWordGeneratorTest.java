package com.dream.helloworld.generator;

import com.dream.generator.AbstractGeneratorHandler;
import com.dream.generator.Generator;
import com.zaxxer.hikari.HikariDataSource;

public class HelloWordGeneratorTest {
    public static void main(String[] args) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://192.168.0.242/d-open?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useAffectedRows=true&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("BMW#Halu@1234%");
        new Generator(dataSource, new GeneratorHandlerImpl()).generate();
    }

    public static class GeneratorHandlerImpl extends AbstractGeneratorHandler {

        @Override
        protected String basePackage() {
            return "com.dream.codegen";
        }

        @Override
        public String author() {
            return "moxa";
        }

        @Override
        public String sourceDir() {
            return "D:\\projects\\dream-project\\dream-orm\\dream-orm-hello-world\\dream-orm-hello-world-generator\\src\\main\\java";
        }

        @Override
        public boolean support(String table) {
            return table.contains("user");
        }
    }
}
