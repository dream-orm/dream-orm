package com.moxa.dream.driver.alias;

import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.Properties;

public abstract class AbstractAliasFactory implements AliasFactory {
    public static final String OPEN_TOKEN = "${";
    public static final String CLOSE_TOKEN = "}";
    private final String openToken;
    private final String closeToken;
    protected Properties properties = new Properties();

    public AbstractAliasFactory() {
        this(OPEN_TOKEN, CLOSE_TOKEN);
    }

    public AbstractAliasFactory(String openToken, String closeToken) {
        this.openToken = openToken;
        this.closeToken = closeToken;
    }


    private String getValue(String value, int pos) {
        if (pos == -1) {
            return value;
        } else {
            String result = "";
            if (pos > 0) {
                result = value.substring(0, pos);
            }
            value = value.substring(pos + openToken.length());
            int openStart = value.indexOf(openToken);
            int closeStart = value.indexOf(closeToken);
            if (closeStart == -1) {
                throw new DreamRunTimeException("开始符号'" + openToken + "'没有匹配结束符号'" + closeToken + "'");
            }
            if (openStart != -1 && openStart < closeStart) {
                StringBuilder variableBuilder = new StringBuilder();
                while (openStart != -1 && openStart < closeStart) {
                    String nextValue = getValue(value, openStart);
                    closeStart = nextValue.indexOf(closeToken);
                    String variable = nextValue.substring(0, closeStart);
                    value = nextValue.substring(closeStart + closeToken.length());
                    openStart = value.indexOf(openToken);
                    closeStart = value.indexOf(closeToken);
                    variableBuilder.append(variable);
                }
                result += getValue(properties, variableBuilder.toString());

            } else {
                result += getValue(properties, value.substring(0, closeStart));
            }
            value = value.substring(closeStart + closeToken.length());
            return result + getValue(value, value.indexOf(openToken));
        }
    }

    protected String getValue(Properties properties, String val) {
        if (!properties.containsKey(val)) {
            throw new DreamRunTimeException("参数变量'" + val + "'未注册");
        }
        return properties.getProperty(val);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = getProp(properties);
    }

    @Override
    public String getValue(String value) {
        if (ObjectUtil.isNull(value))
            return null;
        else
            return getValue(value, value.indexOf(openToken));
    }

    protected abstract Properties getProp(Properties properties);
}
