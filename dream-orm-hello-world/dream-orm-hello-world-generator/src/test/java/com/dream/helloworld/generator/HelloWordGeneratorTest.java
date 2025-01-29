package com.dream.helloworld.generator;

import com.dream.generator.AbstractGeneratorHandler;
import com.dream.generator.Generator;
import com.zaxxer.hikari.HikariDataSource;

public class HelloWordGeneratorTest {
    public static void main(String[] args) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/sooth");
        dataSource.setUsername("postgres");
        dataSource.setPassword("dream.ai@sooth2024");
        new Generator(dataSource, new GeneratorHandlerImpl()).generate();
    }

    public static class GeneratorHandlerImpl extends AbstractGeneratorHandler {

        @Override
        protected String basePackage() {
            return "com.sooth.module.system.language";
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
            return table.contains("language");
        }
    }
}
