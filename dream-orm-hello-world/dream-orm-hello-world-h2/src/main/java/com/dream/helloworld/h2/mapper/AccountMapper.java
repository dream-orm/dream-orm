package com.dream.helloworld.h2.mapper;

import com.dream.helloworld.h2.view.AccountView;
import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.PageQuery;
import com.dream.system.annotation.Param;
import com.dream.system.annotation.Sql;
import com.dream.system.config.Page;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountMapper {
    @Sql("select @*() from account where id=@?(id)")
    AccountView selectById(@Param("id") Integer id);

    @Sql("select @* from account where id>@?(id)")
    List<AccountView> selectList(@Param("id") Integer id);

    @Sql("select @*() from account where @not(id>@?(account.id) and name like concat('%',@?(account.name),'%'))")
    List<AccountView> selectNotList(@Param("account") AccountView accountView);

    @PageQuery
    @Sql("select id, count(id) from account where id>@?(id) group by id order by id")
    List<Map> selectPage(@Param("id") Integer id, @Param("page") Page page);

    @Sql("update account set tenant_id=2,name=@?(account.name),age=@?(account.age) where id=@?(account.id) and tenant_id=3")
    int updateById(@Param("account") AccountView accountView);

    @Sql("update account set @non(name=@?(account.name),age=@?(account.age)) where id=@?(account.id)")
    int updateNonById(@Param("account") AccountView accountView);

    @Sql("insert into account(id,name)values(@?(account.id),@?(account.name))")
    int insert(@Param("account") AccountView accountView);

    @Sql("@insert(account)")
    int invokerInsert(@Param("account") AccountView accountView);

    @Sql("@insertMap(account,map)")
    int invokerInsertMap(@Param("map") Map<String, Object> map);

    @Sql("@insertMaps(account,map)")
    void invokerInsertMaps(@Param("map") List<Map<String, Object>> maps);

    @Sql("delete from account where id=@?(id)")
    int deleteById(@Param("id") Integer id);

    @Sql("delete from account where id in (@foreach(ids))")
    int deleteByIds(@Param("ids") List<Integer> ids);

    @Sql("delete from account where id in (@foreach(accounts,@?(item.id)))")
    int deleteByViews(@Param("accounts") List<AccountView> accountViews);

    @Sql("insert into account(id,name)values @foreach(list,(@?(item.id),@?(item.name)))")
    void insertMany(@Param("list") List<AccountView> accountViews);

}
