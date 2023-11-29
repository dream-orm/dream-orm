package com.dream.helloworld.h2.mapper.provider;

import com.dream.system.provider.ActionProvider;

public class AccountProvider {
    public String selectProvideById(Class type) {
        return "select @*() from account where id=@?(id)";
    }

    public ActionProvider selectProvideList(Class type) {
        return new ActionProvider() {
            @Override
            public String sql() {
                return "select @*() from account where id>@?(account.id)";
            }

        };
    }
}
