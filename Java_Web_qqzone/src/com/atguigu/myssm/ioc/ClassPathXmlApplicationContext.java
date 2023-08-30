package com.atguigu.myssm.ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {


    Map<String,Object> beanMap = new HashMap<>();
    public ClassPathXmlApplicationContext() {
        try {
            //获取文件流
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //创建解析器
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //获取对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            //获取节点列表
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {//
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class baneClass = Class.forName(className); //反射
                    Object beanObj = baneClass.newInstance();
                    beanMap.put(beanId, beanObj);
                }
            }
            //组装依赖
            for (int i = 0; i < beanNodeList.getLength(); i++) {//
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {//需要元素节点
                    Element beanElement = (Element) beanNode;
                    NodeList beanChildNodeList = beanElement.getChildNodes();//需要子节点
                    String beanId = beanElement.getAttribute("id");//
                    for (int j = 0; j < beanChildNodeList.getLength(); j++) {
                        Node beanChildNode = beanChildNodeList.item(j);
                        if (beanChildNode.getNodeType() == Node.ELEMENT_NODE && "property".equals(beanChildNode.getNodeName())) {
                            Element beanChildElement = (Element) beanChildNode;
                            String propertyName = beanChildElement.getAttribute("name");//寻找class中的属性
                            String propertyRef = beanChildElement.getAttribute("ref");//在寻找配置文件中的匹配的ID，找到具体实例
                            //propertyRef对应的实例
                            Object refObj = beanMap.get(propertyRef);
                            //将refObj设置到当前bean对应的实例的property属性上去
                            Object beanObj = beanMap.get(beanId);
                            Class classzz = beanObj.getClass();//返回一个实例
                            Field propertyField = classzz.getDeclaredField(propertyName);//获得方法
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj, refObj);
                        }
                }
            }
            }
        } catch (SAXException | IOException | ParserConfigurationException | ClassNotFoundException |
                 IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
