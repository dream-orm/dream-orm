package com.moxa.dream.module.xml.builder.mapper;

import com.moxa.dream.module.xml.builder.XMLBuilder;
import com.moxa.dream.module.xml.moudle.XmlConstant;
import com.moxa.dream.module.xml.moudle.XmlHandler;
import com.moxa.dream.module.xml.util.XmlUtil;
import org.xml.sax.Attributes;

public class MethodInfoBuilder extends XMLBuilder {
    private MethodInfo methodInfo;

    public MethodInfoBuilder(XmlHandler workHandler) {
        super(workHandler);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case XmlConstant.METHOD:
                methodInfo = XmlUtil.applyAttributes(MethodInfo.class, attributes);
                break;
            case XmlConstant.SQL:
                SqlBuilder sqlXmlBuilder = new SqlBuilder(workHandler);
                sqlXmlBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.EACHLIST:
                EachListBuilder unionsBuilder = new EachListBuilder(workHandler);
                unionsBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.METHOD);
        }
    }

    @Override
    public void characters(String s) {
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return methodInfo;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.SQL:
                SqlBuilder.Sql sql = (SqlBuilder.Sql) obj;
                methodInfo.setSql(sql);
                break;
            case XmlConstant.EACHLIST:
                EachListBuilder.EachList eachList = (EachListBuilder.EachList) obj;
                methodInfo.setEachList(eachList);
                break;
        }
    }

    static class MethodInfo {
        private String name;
        private String timeOut;
        private SqlBuilder.Sql sql;
        private EachListBuilder.EachList eachList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTimeOut() {
            return timeOut;
        }

        public SqlBuilder.Sql getSql() {
            return sql;
        }

        public void setSql(SqlBuilder.Sql sql) {
            this.sql = sql;
        }

        public EachListBuilder.EachList getEachList() {
            return eachList;
        }

        public void setEachList(EachListBuilder.EachList eachList) {
            this.eachList = eachList;
        }
    }
}
