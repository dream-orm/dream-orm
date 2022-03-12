package com.moxa.dream.test.core.view;


import com.moxa.dream.test.core.table.User;

import java.util.List;

//@View("user")
public class ViewUser extends User {

    private List<ViewDept> viewDeptList;

    private ViewDept viewDept;
}
