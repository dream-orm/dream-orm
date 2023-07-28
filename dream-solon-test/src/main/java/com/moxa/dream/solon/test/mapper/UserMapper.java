package com.moxa.dream.solon.test.mapper;

import com.moxa.dream.solon.test.table.User;
import com.moxa.dream.solon.test.view.UserView;
import com.moxa.dream.solon.test.view.UserView2;
import com.moxa.dream.solon.test.view.UserView3;
import com.moxa.dream.solon.test.view.UserView4;
import com.moxa.dream.system.annotation.Mapper;
import com.moxa.dream.system.annotation.PageQuery;
import com.moxa.dream.system.annotation.Param;
import com.moxa.dream.system.annotation.Sql;
import com.moxa.dream.system.config.Page;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Sql("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\tu.id,\n" +
            "\t\tu.NAME,\n" +
            "\t\tu.age,\n" +
            "\t\tu.email,\n" +
            "\t\tb.id bId,\n" +
            "\t\tb.NAME bName \n" +
            "\tFROM\n" +
            "\t\tUSER u\n" +
            "\t\tLEFT JOIN blog b ON b.user_id = u.id \n" +
            "\t) t_tmp \n" +
            "\tLIMIT 1")
    Map findByName(String name);

    @Sql("select @*(),'hello' name from user")
    List<User> findAll();

    @Sql("select id, name, age,email from user")
    List<Object> findByAll();

    @Sql("select id, name, age,email from user order by id")
    @PageQuery("page")
    List<User> findByPage(@Param("page") Page page);

    @Sql("select @*() from @table(user,blog)")
    @PageQuery("page")
    List<UserView3> findByPage2(@Param("page") Page page);


    @Sql("select id, name, age,email from user where name = @rep(name)")
    User findByName2(String name);

    @Sql("delete from user where id in (@foreach(list))")
    int delete(List<Integer> idList);

    @Sql("update user set name=null where state = @?(state)")
    void updateCity(String state);

    @Sql("update user set name=@?(name),email=@?(email) where id=@?(id)")
    Integer update(User user);

    @Sql("update user set @non(name=@?(user.name),age=@?(user.age),email=@?(user.email)) where id=@?(user.id)")
    Integer updateNon(User user);

    @Sql("insert into user(id,name,age,email)values @foreach(list,(@?(item.id),@?(item.name),@?(item.age),@?(item.email)))")
    Integer insertMany(@Param("list") List<User> userList);

    @Sql("insert into user(id,name,age,email)values(@?(item.id),@?(item.name),@?(item.age),@?(item.email))")
    Integer insert(@Param("item") User user);

    @Sql("select @*() from user")
    List<UserView> selectAll();

    @Sql("select @*() from user")
    List<UserView2> selectAll2();

    @Sql("select @*() from @table(user,blog)")
    List<User> selectAll3();

    @Sql("select @*() from user")
    List<UserView4> findAll4();

    @Sql("truncate table user")
    int truncate();

    @Sql("drop table user")
    int drop();
}
