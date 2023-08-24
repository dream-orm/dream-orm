package com.dream.helloworld;

import com.dream.drive.factory.DefaultDriveFactory;
import com.dream.drive.factory.DriveFactory;
import com.dream.drive.transaction.TransManager;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

public class HelloWorldDriveTest {
    public DataSource dataSource;
    private List<String> packageList = Arrays.asList("com.dream.helloworld");

    @Before
    public void before() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl("jdbc:h2:mem:testdb");
        hikariDataSource.setDriverClassName("org.h2.Driver");
        hikariDataSource.setUsername("sa");
        this.dataSource = hikariDataSource;
    }

    @Test
    public void test() {
        DriveFactory driveFactory = new DefaultDriveFactory(dataSource, packageList, packageList);
        Integer value = TransManager.exec(() -> driveFactory.jdbcMapper().queryForObject("select 1", Integer.class));
        System.out.println();
    }
}
