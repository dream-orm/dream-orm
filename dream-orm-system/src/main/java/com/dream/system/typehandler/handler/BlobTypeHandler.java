package com.dream.system.typehandler.handler;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class BlobTypeHandler extends BaseTypeHandler<byte[]> {

    @Override
    public void setParameter(PreparedStatement ps, int index, byte[] parameter, int jdbcType) throws SQLException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(parameter);
        ps.setBinaryStream(index, byteArrayInputStream, parameter.length);
    }

    @Override
    public byte[] getResult(ResultSet rs, int index, int jdbcType) throws SQLException {
        Blob blob = rs.getBlob(index);
        if (null != blob) {
            return blob.getBytes(1, (int) blob.length());
        }
        return null;
    }

    @Override
    public byte[] getResult(ResultSet rs, String column, int jdbcType) throws SQLException {
        Blob blob = rs.getBlob(column);
        if (null != blob) {
            return blob.getBytes(1, (int) blob.length());
        }
        return null;
    }

    @Override
    public int getNullType() {
        return Types.BLOB;
    }
}
