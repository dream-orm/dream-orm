package com.dream.template.wrap;

import com.dream.util.exception.DreamRunTimeException;

public class DefaultWrapper implements Wrapper {
    private Object argValue;

    @Override
    public void init(Class<?> fieldType, String arg) {
        if (arg != null && !arg.isEmpty()) {
            if (fieldType.equals(String.class)) {
                argValue = arg;
            } else if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                argValue = Integer.parseInt(arg);
            } else if (fieldType.equals(float.class) || fieldType.equals(Float.class)) {
                argValue = Float.parseFloat(arg);
            } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                argValue = Double.parseDouble(arg);
            } else if (fieldType.equals(long.class) || fieldType.equals(Long.class)) {
                argValue = Long.parseLong(arg);
            } else if (Enum.class.isAssignableFrom(fieldType)) {
                argValue = Enum.valueOf((Class<? extends Enum>) fieldType, arg);
            } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                argValue = Boolean.parseBoolean(arg);
            } else if (fieldType.equals(char.class) || fieldType.equals(Character.class)) {
                argValue = arg.charAt(0);
            } else if (fieldType.equals(byte.class) || fieldType.equals(Byte.class)) {
                argValue = Byte.parseByte(arg);
            } else if (fieldType.equals(short.class) || fieldType.equals(Short.class)) {
                argValue = Short.parseShort(arg);
            } else {
                throw new DreamRunTimeException("参数类型:'" + fieldType.getName() + "'不支持");
            }
        }
    }

    @Override
    public Object wrap(Object value) {
        if (value == null) {
            return argValue;
        }
        return value;
    }
}
