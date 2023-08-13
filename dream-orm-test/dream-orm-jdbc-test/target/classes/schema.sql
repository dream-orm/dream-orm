DROP TABLE IF EXISTS user;

CREATE TABLE user
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

DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT,
    `name`         varchar(255) DEFAULT NULL COMMENT '文章名称',
    `publish_time` DATE         DEFAULT NULL COMMENT '发表时间',
    `user_id`      BIGINT(20) NOT NULL COMMENT '所属用户',
    tenant_id      INT(11) NULL COMMENT '租户',
    del_flag       int(11) NULL COMMENT '删除标志',
    PRIMARY KEY (`id`)
);
