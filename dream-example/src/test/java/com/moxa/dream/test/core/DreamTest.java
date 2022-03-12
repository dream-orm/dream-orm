package com.moxa.dream.test.core;

import com.moxa.dream.driver.page.Page;
import com.moxa.dream.driver.session.SqlSession;
import com.moxa.dream.driver.session.SqlSessionFactory;
import com.moxa.dream.driver.session.SqlSessionFactoryBuilder;
import com.moxa.dream.module.mapper.MethodInfo;
import com.moxa.dream.module.producer.util.NonCollection;
import com.moxa.dream.test.core.mapper.CityMapper;
import com.moxa.dream.test.core.mapper.UserMapper;
import com.moxa.dream.test.core.table.User;
import com.moxa.dream.test.core.view.MyView;
import com.moxa.dream.test.core.view.ViewUser;
import com.moxa.dream.util.resource.ResourceUtil;

import java.util.List;

public class DreamTest {
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
            .build(ResourceUtil.getResourceAsStream("config.xml"));

    public static void main(String[] args) {
        DreamTest dreamTest = new DreamTest();
//        dreamTest.selectMap();
//        dreamTest.selectBean();
//        dreamTest.selectField();
//        dreamTest.selectTableList();
        dreamTest.selectAll();
//        dreamTest.selectMapper();
//        dreamTest.selectMyView();
//   dreamTest.test();
    }


    public void selectMap() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            User user = new User();
            user.setId(1);
            MethodInfo methodInfo = new MethodInfo
                    .Builder(sqlSessionFactory.getConfiguration())
                    .sql("select * from user where id=@$(id)")
                    .colType(Object.class)
                    .rowType(NonCollection.class)
                    .build();
            Object value = sqlSession.execute(methodInfo, user);
            System.out.println(value);
        }
    }

    public void selectBean() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            User user = new User();
            user.setId(1);
            MethodInfo methodInfo = new MethodInfo
                    .Builder(sqlSessionFactory.getConfiguration())
                    .sql("select * from user where id=@$(id) ")
                    .colType(User.class)
                    .build();
            Object value = sqlSession.execute(methodInfo, user);
            System.out.println(value);
        }
    }

    public void selectField() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            MethodInfo methodInfo = new MethodInfo
                    .Builder(sqlSessionFactory.getConfiguration())
                    .sql("select id from user ")
//                    .rowType(Integer.class)
//                    .colType(List.class)
                    .build();
            Object value = sqlSession.execute(methodInfo, null);
            System.out.println(value);
        }
    }

    public void selectTableList() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            MethodInfo methodInfo = new MethodInfo
                    .Builder(sqlSessionFactory.getConfiguration())
                    .sql("select user.id,user.name,dept.id,dept.name from user left join user_dept on user.id=user_dept.user_id left join dept on dept.id=user_dept.dept_id ")
                    .colType(ViewUser.class)
                    .rowType(List.class)
                    .build();
            Object value = sqlSession.execute(methodInfo, null);
            System.out.println(value);
        }
    }

    public void selectAll() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            MethodInfo methodInfo = new MethodInfo
                    .Builder(sqlSessionFactory.getConfiguration())
//                    .sql("select @all(),user.id  from user left join user_dept on user.id=user_dept.user_id left join dept on dept_id=dept.id")
                    .sql("select @all(user),11 id  from user")
                    .colType(ViewUser.class)
                    .rowType(List.class)
                    .build();
            Object value = sqlSession.execute(methodInfo, null);
            System.out.println(value);
        }
    }

    public void selectMapper() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(false, false, false)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
//            Object o = mapper.selectUserById(1);
            Page page = new Page(1, 1);
            Page<ViewUser> viewUserList = mapper.selectUserList(page);
        }

    }

    public void selectMyView() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            User user = new User();
            user.setId(1);
            MethodInfo methodInfo = new MethodInfo
                    .Builder(sqlSessionFactory.getConfiguration())
                    .sql("select * from user where id=@$(id) ")
                    .colType(MyView.class)
                    .build();
            Object value = sqlSession.execute(methodInfo, user);
            System.out.println(value);
        }
    }

    public void test() {
        long l = System.currentTimeMillis();

        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {

            for (int i = 0; i < 1000000; i++) {

                CityMapper cityMapper = sqlSession.getMapper(CityMapper.class);

                cityMapper.findByState("CA" + i);
            }

        }
        System.out.println(System.currentTimeMillis() - l);
    }
}
