package com.moxa.dream.test.reflect;


import com.moxa.dream.test.core.view.ViewUser;
import com.moxa.dream.util.wrapper.ObjectWrapper;


public class ReflectTest {

    public static void main(String[] args) {
        ViewUser viewUser = new ViewUser();
        long l = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            ObjectWrapper wrapper = ObjectWrapper.wrapper(viewUser);
            wrapper.set("id", i);
            wrapper.set("name", "name" + i);
            wrapper.set("viewDept.id", i);
            wrapper.set("viewDept.name", "name" + i);
        }
        System.out.println(System.currentTimeMillis() - l);
    }
}
