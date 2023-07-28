package com.moxa.dream.solon.test.provider;


import com.moxa.dream.system.provider.ActionProvider;

public class BlogMapperProvider {
    public ActionProvider selectBlogByUserId2() {
        return new BlogActionProvider();
    }
//    public String selectBlogByUserId2() {
//        return "select @*() from blog where user_id=@?(userId)";
//    }
}
