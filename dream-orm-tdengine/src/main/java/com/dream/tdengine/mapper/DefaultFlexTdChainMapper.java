package com.dream.tdengine.mapper;

import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.def.TableDef;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.def.TdChainDeleteTableDef;
import com.dream.tdengine.def.TdChainInsertIntoTableDef;
import com.dream.tdengine.def.TdChainSelectDef;
import com.dream.tdengine.def.TdChainUpdateColumnDef;
import com.dream.tdengine.factory.TdChainDeleteCreatorFactory;
import com.dream.tdengine.factory.TdChainInsertCreatorFactory;
import com.dream.tdengine.factory.TdChainQueryCreatorFactory;
import com.dream.tdengine.factory.TdChainUpdateCreatorFactory;

public class DefaultFlexTdChainMapper implements FlexTdChainMapper {
    private FlexMapper flexMapper;

    public DefaultFlexTdChainMapper(FlexMapper flexMapper) {
        this.flexMapper = flexMapper;
    }

    @Override
    public TdChainSelectDef select(ColumnDef... columnDefs) {
        return new TdChainQueryCreatorFactory(flexMapper).newQueryDef().select(columnDefs);
    }

    @Override
    public TdChainUpdateColumnDef update(TableDef tableDef) {
        return new TdChainUpdateCreatorFactory(flexMapper).newUpdateDef().update(tableDef);
    }

    @Override
    public TdChainInsertIntoTableDef insertInto(String subTableName) {
        return new TdChainInsertCreatorFactory(flexMapper).newInsertDef().insertInto(FunctionDef.table(subTableName));
    }

    @Override
    public TdChainDeleteTableDef delete(TableDef tableDef) {
        return new TdChainDeleteCreatorFactory(flexMapper).newDeleteDef().delete(tableDef);
    }
}
