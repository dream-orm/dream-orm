package com.moxa.dream.system.typehandler.factory;

import com.moxa.dream.system.typehandler.TypeHandlerNotFoundException;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;
import com.moxa.dream.system.typehandler.wrapper.*;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.HashMap;
import java.util.Map;


public class DefaultTypeHandlerFactory implements TypeHandlerFactory {
    protected Map<Integer, TypeHandler> typeHandlerMap = new HashMap<>(4);

    public DefaultTypeHandlerFactory() {
        wrappers(getTypeHandlerWrappers());
    }

    @Override
    public void wrappers(TypeHandlerWrapper... typeHandlerWrappers) {
        if (!ObjectUtil.isNull(typeHandlerWrappers)) {
            for (TypeHandlerWrapper typeHandlerWrapper : typeHandlerWrappers) {
                TypeHandler typeHandler = typeHandlerWrapper.getTypeHandler();
                if (typeHandler != null) {
                    typeHandler = wrapper(typeHandler);
                    Integer[] typeCode = typeHandlerWrapper.typeCode();
                    if (!ObjectUtil.isNull(typeCode)) {
                        for (int typeHandlerCode : typeCode) {
                            typeHandlerMap.put(typeHandlerCode, typeHandler);
                        }
                    }
                }
            }
        }
    }

    @Override
    public TypeHandler getTypeHandler(Class javaType, int jdbcType) throws TypeHandlerNotFoundException {
        TypeHandler typeHandler = getTypeHandler(TypeUtil.hash(javaType, jdbcType));
        if (typeHandler == null) {
            return getNoneTypeHandler(javaType, jdbcType);
        } else {
            return typeHandler;
        }
    }

    protected TypeHandler getTypeHandler(int typeCode) {
        TypeHandler typeHandler = typeHandlerMap.get(typeCode);
        return typeHandler;
    }

    protected TypeHandlerWrapper[] getTypeHandlerWrappers() {
        return new TypeHandlerWrapper[]{
                new BigDecimalTypeHandlerWrapper(),
                new BlobInputStreamTypeHandlerWrapper(),
                new BlobTypeHandlerWrapper(),
                new BooleanTypeHandlerWrapper(),
                new ByteTypeHandlerWrapper(),
                new SqlDateTypeHandlerWrapper(),
                new UtilDateTypeHandlerWrapper(),
                new DoubleTypeHandlerWrapper(),
                new FloatTypeHandlerWrapper(),
                new IntegerTypeHandlerWrapper(),
                new LongTypeHandlerWrapper(),
                new ShortTypeHandlerWrapper(),
                new StringTypeHandlerWrapper(),
                new TimeStampTypeHandlerWrapper(),
                new TimeTypeHandlerWrapper(),
                new ObjectTypeHandlerWrapper()
        };
    }

    protected TypeHandler wrapper(TypeHandler typeHandler) {
        return typeHandler;
    }

    protected TypeHandler getNoneTypeHandler(Class javaType, int jdbcType) throws TypeHandlerNotFoundException {
        throw new TypeHandlerNotFoundException("类型选择器未找到,javaType '" + javaType.getTypeName() + "',jdbcType '" + TypeUtil.getTypeName(jdbcType) + "'");
    }
}
