package com.moxa.dream.share.mapper;

import com.moxa.dream.mate.share.annotation.Share;
import com.moxa.dream.share.table.User;
import com.moxa.dream.system.annotation.Mapper;
import com.moxa.dream.system.annotation.Sql;

import java.util.List;

@Mapper
@Share("master")
public interface UserMapper {
    @Sql("select id, name, age,email from user where name = @?(name)")
    List<User> findByName(String name);

    @Share("slave")
    @Sql("select id, name, age,email from user where name = @rep(name)")
    List<User> findByName2(String name);

    @Sql("insert into user(id,name,age,email)values(@?(user.id),@?(user.name),@?(user.age),@?(user.email))")
    int insert(User user);

}
