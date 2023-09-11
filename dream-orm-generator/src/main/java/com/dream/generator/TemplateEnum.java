package com.dream.generator;

public enum TemplateEnum {
    BO("./template/bo.ftl"),
    CONTROLLER("./template/controller.ftl"),
    DTO("./template/dto.ftl"),
    SERVICE("./template/service.ftl"),
    SERVICE_IMPL("./template/serviceImpl.ftl"),
    TABLE("./template/table.ftl"),
    VO("./template/vo.ftl");
    String path;

    TemplateEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
