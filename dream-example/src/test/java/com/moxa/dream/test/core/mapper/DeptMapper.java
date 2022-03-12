package com.moxa.dream.test.core.mapper;

import com.moxa.dream.module.hold.annotation.Mapper;
import com.moxa.dream.module.hold.annotation.Sql;
import com.moxa.dream.test.core.view.ViewDept;

import java.util.List;

@Mapper
public interface DeptMapper {
    @Sql("select dept.* from dept inner join user_dept on dept.id=user_dept.dept_id where user_dept.user_id=@$(id) ")
    List<ViewDept> selectViewDeptList(int id);
}
