package com.dream.helloworld;

import com.dream.drive.config.DriveProperties;
import com.dream.drive.factory.DefaultDriveFactory;
import com.dream.drive.factory.DriveFactory;
import com.dream.helloworld.table.Account;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class HelloWorldDriveTest {
    DriveFactory driveFactory;

    @Before
    public void before() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:h2:mem:testdb");
        hikariDataSource.setDriverClassName("org.h2.Driver");
        hikariDataSource.setUsername("sa");
        List<String> packageList = Arrays.asList("com.dream.helloworld");
        String sql = "DROP TABLE IF EXISTS account;\n" +
                "\n" +
                "CREATE TABLE account\n" +
                "(\n" +
                "    id        BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',\n" +
                "    name      VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',\n" +
                "    age       INT(11) NULL DEFAULT NULL COMMENT '年龄',\n" +
                "    email     VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',\n" +
                "    tenant_id INT(11) NULL COMMENT '租户',\n" +
                "    dept_id   int(11) NULL COMMENT '所在部门',\n" +
                "    del_flag  int(11) NULL COMMENT '删除标志',\n" +
                "    PRIMARY KEY (id)\n" +
                ");DELETE\n" +
                "FROM account;\n" +
                "\n" +
                "INSERT INTO account (id, name, age, email, tenant_id, dept_id, del_flag)\n" +
                "VALUES (1, 'Jone', 18, 'test1', 1, 1, 0),\n" +
                "       (2, 'Jack', 20, 'test2', 1, 1, 0),\n" +
                "       (3, 'Tom', 28, 'test3', 1, 1, 0),\n" +
                "       (4, 'Sandy', 21, 'test4', 2, 2, 0),\n" +
                "       (5, 'Billie', 24, 'test5', 2, 2, 0);";

        driveFactory = new DefaultDriveFactory(hikariDataSource, packageList, packageList,new DriveProperties());
        driveFactory.jdbcMapper().execute(sql);
    }

    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        driveFactory.templateMapper().selectById(Account.class, 1);
    }
}
