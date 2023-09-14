package com.dream.helloworld.h2.mapper;

import com.dream.helloworld.h2.mapper.provider.AccountProvider;
import com.dream.helloworld.h2.view.AccountView;
import com.dream.system.annotation.Mapper;
import com.dream.system.annotation.Param;

import java.util.List;

@Mapper(AccountProvider.class)
public interface ProviderAccountMapper {
    AccountView selectProvideById(@Param("id") int id);

    List<AccountView> selectProvideList(@Param("account") AccountView accountView);

}
