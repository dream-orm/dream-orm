package com.dream.helloworld.generator;

import com.dream.generator.AbstractGeneratorHandler;
import com.dream.generator.Generator;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;

public class HelloWordGeneratorTest {
    public static void main(String[] args) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1/sooth?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useAffectedRows=true&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("moxa@sooth");
        new Generator(dataSource, new GeneratorHandlerImpl()).generate();
    }

    public static class GeneratorHandlerImpl extends AbstractGeneratorHandler {

        @Override
        protected String basePackage() {
            return "com.sooth.module.gpt.session";
        }

        @Override
        public String author() {
            return "moxa";
        }

        @Override
        public String sourceDir() {
            return "/Users/moxa/Desktop/Projects/dream-orm/dream-orm-hello-world/dream-orm-hello-world-generator/src/main/java";
        }

        @Override
        public boolean support(String table) {
            return table.contains("session");
        }
    }
}
