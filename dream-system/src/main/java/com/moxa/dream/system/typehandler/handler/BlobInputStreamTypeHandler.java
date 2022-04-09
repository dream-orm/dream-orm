package com.moxa.dream.system.typehandler.handler;

import java.io.InputStream;
import java.sql.*;

public class BlobInputStreamTypeHandler extends BaseTypeHandler<InputStream> {

    @Override
    public void setParameter(PreparedStatement ps, int i, InputStream parameter, int jdbcType) throws SQLException {
        ps.setBlob(i, parameter);
    }

    @Override
    public InputStream getResult(ResultSet rs, int columnIndex, int jdbcType) throws SQLException {
        Blob blob = rs.getBlob(columnIndex);
        if (blob != null)
            return blob.getBinaryStream();
        return null;
    }

    @Override
    public int getNullType() {
        return Types.BLOB;
    }
}
