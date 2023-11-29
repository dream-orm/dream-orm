package com.dream.helloworld.h2.mapper;

import com.dream.helloworld.h2.mapper.provider.AccountProvider;
import com.dream.helloworld.h2.view.AccountView;
import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.Param;
import com.dream.system.annotation.Provider;

import java.util.List;

@Mapper
public interface ProviderAccountMapper {
    @Provider(type = AccountProvider.class)
    AccountView selectProvideById(@Param("id") int id);
    @Provider(type = AccountProvider.class)
    List<AccountView> selectProvideList(@Param("account") AccountView accountView);
}
