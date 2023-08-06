package com.moxa.dream.flex.test.table.table;

import com.moxa.dream.flex.def.ColumnDef;
import com.moxa.dream.flex.def.TableDef;

public class BlogTableDef extends TableDef {

    public static final BlogTableDef blog = new BlogTableDef();

    public final ColumnDef id = new ColumnDef(this,"id");
    public final ColumnDef name = new ColumnDef(this,"name");
    public final ColumnDef publish_time = new ColumnDef(this,"publish_time");
    public final ColumnDef user_id = new ColumnDef(this,"user_id");
    public final ColumnDef tenant_id = new ColumnDef(this,"tenant_id");
    public final ColumnDef del_flag = new ColumnDef(this,"del_flag");

    public final ColumnDef[]columns = new ColumnDef[]{id,name,publish_time,user_id,tenant_id,del_flag};
    public final ColumnDef[]blogView = new ColumnDef[]{id,name,publish_time};

    public BlogTableDef() {
        this(null);
    }
    public BlogTableDef(String alias) {
        super("blog",alias);
    }

}
