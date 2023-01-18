package com.moxa.dream.system.typehandler.handler;

import java.io.InputStream;
import java.sql.*;

public class BlobInputStreamTypeHandler extends BaseTypeHandler<InputStream> {

    @Override
    public void setParameter(PreparedStatement ps, int index, InputStream parameter, int jdbcType) throws SQLException {
        ps.setBlob(index, parameter);
    }

    @Override
    public InputStream getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        Blob blob = rs.getBlob(index);
        if (blob != null) {
            return blob.getBinaryStream();
        }
        return null;
    }

    @Override
    public InputStream getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        Blob blob = rs.getBlob(column);
        if (blob != null) {
            return blob.getBinaryStream();
        }
        return null;
    }

    @Override
    public int getNullType() {
        return Types.BLOB;
    }
}
