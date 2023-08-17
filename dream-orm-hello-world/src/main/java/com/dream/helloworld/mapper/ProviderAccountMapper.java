package com.dream.helloworld.mapper;

import com.dream.helloworld.mapper.provider.AccountProvider;
import com.dream.helloworld.view.AccountView;
import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.Param;

import java.util.List;

@Mapper(AccountProvider.class)
public interface ProviderAccountMapper {
    AccountView selectProvideById(@Param("id") int id);

    List<AccountView> selectProvideList(@Param("account") AccountView accountView);

}
