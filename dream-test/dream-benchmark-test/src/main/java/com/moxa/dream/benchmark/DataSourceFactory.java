package com.moxa.dream.benchmark;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSourceFactory {

    public static void initData(DataSource dataSource) {
        for (int i = 1; i <= 20000; i++) {
            try (Connection connection = dataSource.getConnection()) {
                String userName = "admin" + i;

                String sql = "INSERT INTO account (" +
                        "user_name, password, salt, nickname, email, mobile, avatar, type, status, created)" +
                        " VALUES " +
                        "('" + userName + "', 'ba95c7c416de308531529c43', 'BgS1qIxWd_5-RFmePSs9BJmLQ5ejxZyt', 'Michael', 'michael@gmail.com', NULL, NULL, NULL, NULL, NULL);";
                Statement statement = connection.createStatement();
                statement.execute(sql);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
