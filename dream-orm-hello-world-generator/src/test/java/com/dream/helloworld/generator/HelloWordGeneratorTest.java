package com.dream.helloworld.generator;

import com.dream.generator.Generator;
import com.dream.generator.GeneratorHandler;
import com.dream.system.util.SystemUtil;
import com.zaxxer.hikari.HikariDataSource;

public class HelloWordGeneratorTest {
    public static void main(String[] args) {
        HikariDataSource dataSource=new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://192.168.0.242/d-open?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useAffectedRows=true&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai");
        dataSource.setUsername("root");
        dataSource.setPassword("BMW#Halu@1234%");
        new Generator(dataSource,new GeneratorHandlerImpl()).generate();
    }

    public static class GeneratorHandlerImpl implements GeneratorHandler {
        public String className(String table) {
            String name = SystemUtil.underlineToCamel(table);
            return Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }

        @Override
        public String author() {
            return "moxa";
        }

        @Override
        public boolean override(String table) {
            return true;
        }

        @Override
        public boolean support(String table) {
            if (table.contains("user")) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String sourceDir() {
            return "D:\\projects\\dream-project\\dream-orm\\dream-orm-hello-world-generator\\src\\main\\java\\";
        }

        @Override
        public String controllerClassName(String table) {
            return "com.dream.gen.controller." + className(table) + "Controller";
        }

        @Override
        public String serviceClassName(String table) {
            return "com.dream.gen.service." + className(table) + "Service";
        }

        @Override
        public String serviceImplClassName(String table) {
            return "com.dream.gen.service." + className(table) + "ServiceImpl";
        }

        @Override
        public String tableClassName(String table) {
            return "com.dream.gen.table." + className(table);
        }

        @Override
        public String voClassName(String table) {
            return "com.dream.gen.view." + className(table) + "Vo";
        }

        @Override
        public String boClassName(String table) {
            return "com.dream.gen.view." + className(table) + "Bo";
        }

        @Override
        public String dtoClassName(String table) {
            return "com.dream.gen.view." + className(table) + "Dto";
        }

        @Override
        public String requestMapping(String table) {
            return "/"+table.replace("_","/");
        }
    }
}
