package com.dream.helloworld.mapper.provider;

import com.dream.system.core.action.Action;
import com.dream.system.provider.ActionProvider;

public class AccountProvider {
    public String selectProvideById() {
        return "select @*() from account where id=@?(id)";
    }

    public ActionProvider selectProvideList() {
        return new ActionProvider() {
            @Override
            public String sql() {
                return "select @*() from account where id>@?(account.id)";
            }

            @Override
            public Action[] initActionList() {
                return new Action[]{(session, mappedStatement, arg) -> System.out.println("sql执行前自定义操作")};
            }

            @Override
            public Action[] destroyActionList() {
                return new Action[]{(session, mappedStatement, arg) -> System.out.println("sql执行后自定义操作")};
            }
        };
    }
}
