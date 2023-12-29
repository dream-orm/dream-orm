package com.dream.tdengine.mapper;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.sql.ToSQL;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.TableDef;
import com.dream.flex.dialect.AbstractFlexDialect;
import com.dream.flex.mapper.DefaultFlexMapper;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.core.session.Session;
import com.dream.tdengine.def.TdChainDeleteWhereDef;
import com.dream.tdengine.def.TdChainFromDef;
import com.dream.tdengine.def.TdChainInsertIntoColumnsDef;
import com.dream.tdengine.def.TdChainUpdateColumnDef;
import com.dream.tdengine.factory.TdChainDeleteCreatorFactory;
import com.dream.tdengine.factory.TdChainInsertCreatorFactory;
import com.dream.tdengine.factory.TdChainQueryCreatorFactory;
import com.dream.tdengine.factory.TdChainUpdateCreatorFactory;
import com.dream.tdengine.sql.ToTdEngine;

import java.util.List;

public class DefaultFlexTdChainMapper implements FlexTdChainMapper {
    private FlexMapper flexMapper;

    public DefaultFlexTdChainMapper(Session session) {
        this(session, new ToTdEngine());
    }

    public DefaultFlexTdChainMapper(Session session, ToSQL toSQL) {
        this.flexMapper = new DefaultFlexMapper(session, new AbstractFlexDialect(toSQL) {
            @Override
            protected List<Invoker> invokerList() {
                return null;
            }
        });
    }

    @Override
    public TdChainFromDef select(ColumnDef... columnDefs) {
        return new TdChainQueryCreatorFactory(flexMapper).newSelectDef().select(columnDefs);
    }

    @Override
    public TdChainFromDef select(boolean distinct, ColumnDef... columnDefs) {
        return new TdChainQueryCreatorFactory(flexMapper).newSelectDef().select(distinct, columnDefs);
    }

    @Override
    public TdChainUpdateColumnDef update(TableDef tableDef) {
        return new TdChainUpdateCreatorFactory(flexMapper).newUpdateTableDef().update(tableDef);
    }

    @Override
    public TdChainInsertIntoColumnsDef insertInto(String subTableName) {
        return new TdChainInsertCreatorFactory(flexMapper).newInsertIntoTableDef().insertInto(FunctionDef.table(subTableName));
    }

    @Override
    public TdChainDeleteWhereDef delete(TableDef tableDef) {
        return new TdChainDeleteCreatorFactory(flexMapper).newDeleteTableDef().delete(tableDef);
    }
}
