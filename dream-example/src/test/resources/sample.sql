DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept`
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `dept`
VALUES (1, '部门1');
INSERT INTO `dept`
VALUES (2, '部门2');
INSERT INTO `dept`
VALUES (3, '部门3');
INSERT INTO `dept`
VALUES (4, '部门4');
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) DEFAULT NULL,
    `create_time` datetime     DEFAULT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `user`
VALUES (1, 'lijz', NULL);
INSERT INTO `user`
VALUES (2, 'lucy', NULL);
INSERT INTO `user`
VALUES (3, 'bear', NULL);
INSERT INTO `user`
VALUES (4, 'mike', NULL);
INSERT INTO `user`
VALUES (5, 'lisan', NULL);
INSERT INTO `user`
VALUES (6, 'xb', NULL);
INSERT INTO `user`
VALUES (7, 'duanwu', NULL);
INSERT INTO `user`
VALUES (8, 'fenh', NULL);
INSERT INTO `user`
VALUES (9, 'lj', NULL);
INSERT INTO `user`
VALUES (10, 'gshen', NULL);
INSERT INTO `user`
VALUES (11, 'lihui', NULL);
DROP TABLE IF EXISTS `user_dept`;
CREATE TABLE `user_dept`
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) DEFAULT NULL,
    `dept_id` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `user_dept`
VALUES (1, 1, 2);
INSERT INTO `user_dept`
VALUES (2, 2, 1);
INSERT INTO `user_dept`
VALUES (3, 3, 1);
INSERT INTO `user_dept`
VALUES (4, 4, 1);
INSERT INTO `user_dept`
VALUES (5, 5, 3);
INSERT INTO `user_dept`
VALUES (6, 6, 2);
INSERT INTO `user_dept`
VALUES (7, 7, 1);
INSERT INTO `user_dept`
VALUES (8, 8, 3);
INSERT INTO `user_dept`
VALUES (9, 9, 3);
INSERT INTO `user_dept`
VALUES (10, 10, 1);
INSERT INTO `user_dept`
VALUES (11, 11, 2);
INSERT INTO `user_dept`
VALUES (12, 1, 1);
INSERT INTO `user_dept`
VALUES (13, 2, 2);
INSERT INTO `user_dept`
VALUES (14, 3, 3);


drop table if exists city;
create table city (id int primary key auto_increment, name varchar, state varchar, country varchar);
insert into city (name, state, country) values ('San Francisco', 'CA', 'US');
