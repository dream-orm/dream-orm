package com.dream.test.table.table;

import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.TableDef;

public class UserTableDef extends TableDef {

    public static final UserTableDef user = new UserTableDef();

    public final ColumnDef id = new ColumnDef(this,"id");
    public final ColumnDef name = new ColumnDef(this,"name");
    public final ColumnDef age = new ColumnDef(this,"age");
    public final ColumnDef email = new ColumnDef(this,"email");
    public final ColumnDef tenant_id = new ColumnDef(this,"tenant_id");
    public final ColumnDef dept_id = new ColumnDef(this,"dept_id");
    public final ColumnDef del_flag = new ColumnDef(this,"del_flag");

    public final ColumnDef[]columns = new ColumnDef[]{id,name,age,email,tenant_id,dept_id,del_flag};
    public final ColumnDef[]userView = new ColumnDef[]{id,name,email};

    public UserTableDef() {
        this(null);
    }
    public UserTableDef(String alias) {
        super("user",alias);
    }

}
