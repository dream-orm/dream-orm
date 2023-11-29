package com.dream.helloworld.h2.mapper.provider;

import com.dream.system.config.MappedStatement;
import com.dream.system.core.action.DestroyAction;
import com.dream.system.core.action.InitAction;
import com.dream.system.core.action.LoopAction;
import com.dream.system.core.session.Session;
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

            @Override
            public InitAction initAction() {
                return new InitAction() {
                    @Override
                    public void init(MappedStatement mappedStatement, Session session) {
                        System.out.println("sql执行前自定义操作");
                    }
                };
            }

            @Override
            public LoopAction loopAction() {
                return new LoopAction() {
                    @Override
                    public void loop(Object row, MappedStatement mappedStatement, Session session) {
                        System.out.println("查询结果遍历自定义操作");
                    }
                };
            }

            @Override
            public DestroyAction destroyAction() {
                return new DestroyAction() {
                    @Override
                    public Object destroy(Object result, MappedStatement mappedStatement, Session session) {
                        System.out.println("sql执行后自定义操作");
                        return result;
                    }
                };
            }
        };
    }
}
