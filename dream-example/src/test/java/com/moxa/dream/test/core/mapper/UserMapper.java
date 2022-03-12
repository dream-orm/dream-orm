package com.moxa.dream.test.core.mapper;

import com.moxa.dream.driver.annotation.AutoPage;
import com.moxa.dream.driver.page.Page;
import com.moxa.dream.module.hold.annotation.Mapper;
import com.moxa.dream.module.hold.annotation.Sql;
import com.moxa.dream.test.core.view.ViewUser;

@Mapper("mapper.xml")
public interface UserMapper {
    public ViewUser selectUserById(int id);

    @AutoPage
    @Sql(value = "select * from user")
    public Page<ViewUser> selectUserList(Page page);
}
