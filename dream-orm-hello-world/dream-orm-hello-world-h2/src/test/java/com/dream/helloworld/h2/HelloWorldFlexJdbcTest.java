package com.dream.helloworld.h2;

import com.dream.helloworld.h2.view.AccountView;
import com.dream.jdbc.core.StatementSetter;
import com.dream.jdbc.mapper.JdbcMapper;
import com.dream.jdbc.row.RowMapping;
import com.dream.system.config.MappedStatement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloWorldApplication.class)
public class HelloWorldFlexJdbcTest {
    @Autowired
    private JdbcMapper jdbcMapper;

    /**
     * 测试主键查询
     */
    @Test
    public void testSelectById() {
        AccountView accountView = jdbcMapper.queryForObject("select id,name from account where id=?", AccountView.class, 1);
        System.out.println("查询结果：" + accountView);
    }

    /**
     * 测试查询多条
     */
    @Test
    public void testSelectList() {
        List<AccountView> accountViews = jdbcMapper.queryForList("select id,name from account where id>?", AccountView.class, 3);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试查询多条且手动设置参数
     */
    @Test
    public void testSelectList2() {
        List<AccountView> accountViews = jdbcMapper.queryForList("select id,name from account where id>?", new StatementSetter() {
            @Override
            public void setter(PreparedStatement ps, MappedStatement mappedStatement) throws SQLException {
                ps.setInt(1, 3);
            }
        }, AccountView.class);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试查询多条且手动映射
     */
    @Test
    public void testSelectListMapping() {
        List<AccountView> accountViews = jdbcMapper.queryForList("select id,name from account where id>?", new RowMapping<AccountView>() {
            @Override
            public AccountView mapTow(ResultSet resultSet) throws SQLException {
                AccountView accountView = new AccountView();
                accountView.setId(resultSet.getInt(1));
                accountView.setName(resultSet.getString(2));
                return accountView;
            }
        }, 3);
        System.out.println("查询结果：" + accountViews);
    }

    /**
     * 测试更新
     */
    @Test
    public void testUpdate() {
        jdbcMapper.execute("update account set name=? where id=?", "accountName", 1);
    }

    /**
     * 更新操作且手动设置参数
     */
    @Test
    public void testUpdate2() {
        AccountView accountView = new AccountView();
        accountView.setName("accountName");
        accountView.setId(1);
        jdbcMapper.execute("update account set name=? where id=?", new StatementSetter() {
            @Override
            public void setter(PreparedStatement ps, MappedStatement mappedStatement) throws SQLException {
                ps.setString(1, accountView.getName());
                ps.setInt(2, accountView.getId());
            }
        });
    }

    /**
     * 测试插入
     */
    @Test
    public void testInsert() {
        jdbcMapper.execute("insert into account(id,name)values(?,?)", 400, "accountName");
    }

    /**
     * 测试插入且手动设置参数
     */
    @Test
    public void testInsert2() {
        AccountView accountView = new AccountView();
        accountView.setName("accountName");
        accountView.setId(401);
        jdbcMapper.execute("insert into account(id,name)values(?,?)", new StatementSetter() {
            @Override
            public void setter(PreparedStatement ps, MappedStatement mappedStatement) throws SQLException {
                ps.setInt(1, accountView.getId());
                ps.setString(2, accountView.getName());
            }
        });
    }

    /**
     * 测试删除
     */
    @Test
    public void testDelete() {
        jdbcMapper.execute("delete from account where id=?", 1);
    }

    /**
     * 测试删除且手动设置参数
     */
    @Test
    public void testDelete2() {
        AccountView accountView = new AccountView();
        accountView.setId(1);
        jdbcMapper.execute("delete from account where id=?", new StatementSetter() {
            @Override
            public void setter(PreparedStatement ps, MappedStatement mappedStatement) throws SQLException {
                ps.setInt(1, accountView.getId());
            }
        });
    }
}
