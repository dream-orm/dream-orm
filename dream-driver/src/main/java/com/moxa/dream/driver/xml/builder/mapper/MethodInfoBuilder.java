package com.moxa.dream.driver.xml.builder.mapper;

import com.moxa.dream.driver.xml.builder.XMLBuilder;
import com.moxa.dream.driver.xml.moudle.XmlConstant;
import com.moxa.dream.driver.xml.moudle.XmlHandler;
import com.moxa.dream.driver.xml.util.XmlUtil;
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
            case XmlConstant.INIT:
                InitBuilder initBuilder=new InitBuilder(workHandler);
                initBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.LOOP:
                LoopBuilder loopBuilder=new LoopBuilder(workHandler);
                loopBuilder.startElement(uri, localName, qName, attributes);
                break;
            case XmlConstant.DESTROY:
                DestroyBuilder destroyBuilder=new DestroyBuilder(workHandler);
                destroyBuilder.startElement(uri, localName, qName, attributes);
                break;
            default:
                throwXmlException(uri, localName, qName, attributes, XmlConstant.METHOD);
        }
    }

    @Override
    public void characters(String s) {
        methodInfo.value= methodInfo.value+s;
    }

    @Override
    public Object endElement(String uri, String localName, String qName) {
        return methodInfo;
    }

    @Override
    public void builder(String uri, String localName, String qName, Object obj) {
        switch (qName) {
            case XmlConstant.INIT:
                methodInfo.init=(InitBuilder.Init) obj;
                break;
            case XmlConstant.LOOP:
                methodInfo.loop=(LoopBuilder.Loop) obj;
                break;
            case XmlConstant.DESTROY:
                methodInfo.destroy=(DestroyBuilder.Destroy) obj;
                break;
        }
    }

    static class MethodInfo {
        private String name;
        private String timeOut;

        private String value="";

        private InitBuilder.Init init;
        private LoopBuilder.Loop loop;
        private DestroyBuilder.Destroy destroy;
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTimeOut() {
            return timeOut;
        }

        public String getValue() {
            return value;
        }

        public InitBuilder.Init getInit() {
            return init;
        }

        public LoopBuilder.Loop getLoop() {
            return loop;
        }

        public DestroyBuilder.Destroy getDestroy() {
            return destroy;
        }
    }
}
