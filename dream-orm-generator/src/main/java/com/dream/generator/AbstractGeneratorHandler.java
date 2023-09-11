package com.dream.generator;

import com.dream.system.util.SystemUtil;

public abstract class AbstractGeneratorHandler implements GeneratorHandler {

    protected abstract String basePackage();

    protected String moduleName(String table) {
        return null;
    }

    protected String className(String table) {
        String name = SystemUtil.underlineToCamel(table);
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    private String packageName(String table) {
        String basePackage = basePackage();
        String moduleName = moduleName(table);
        if (moduleName == null || moduleName.isEmpty()) {
            return basePackage;
        } else {
            return basePackage + "." + moduleName;
        }
    }

    @Override
    public String controllerClassName(String table) {
        return packageName(table) + ".controller." + className(table) + "Controller";
    }

    @Override
    public String serviceClassName(String table) {
        return packageName(table) + ".service.I" + className(table) + "Service";
    }

    @Override
    public String serviceImplClassName(String table) {
        return packageName(table) + ".service." + className(table) + "ServiceImpl";
    }

    @Override
    public String tableClassName(String table) {
        return packageName(table) + ".table." + className(table);
    }

    @Override
    public String voClassName(String table) {
        return packageName(table) + ".view." + className(table) + "Vo";
    }

    @Override
    public String boClassName(String table) {
        return packageName(table) + ".view." + className(table) + "Bo";
    }

    @Override
    public String dtoClassName(String table) {
        return packageName(table) + ".view." + className(table) + "Dto";
    }
}
