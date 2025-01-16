DROP TABLE IF EXISTS account;

CREATE TABLE account
(
    id        BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name      VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    age       INT(11) NULL DEFAULT NULL COMMENT '年龄',
    email     VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    tenant_id INT(11) NULL COMMENT '租户',
    dept_id   int(11) NULL COMMENT '所在部门',
    del_flag  int(11) NULL COMMENT '删除标志',
    PRIMARY KEY (id)
);

CREATE TABLE dept
(
    id        BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    name      VARCHAR(30) NULL DEFAULT NULL COMMENT '部门名称',
    tenant_id INT(11) NULL COMMENT '租户',
    del_flag  int(11) NULL COMMENT '删除标志',
    PRIMARY KEY (id)
);
