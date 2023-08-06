package com.dream.solon.test.mapper;

import com.dream.solon.test.table.User;
import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.Sql;

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


    @Sql("select id, name, age,email from user where name = @rep(name)")
    User findByName2(String name);


    @Sql("update user set name=@?(name),email=@?(email) where id=@?(id)")
    Integer update(User user);

}
