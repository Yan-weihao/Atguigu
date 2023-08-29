package com.atguigu.fruit.dao.myspringmvc;

import com.atguigu.fruit.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器
 */
@WebServlet("*.do") //拦截全部.do后缀
public class DispatcherServlet extends ViewBaseServlet{
    private Map<String ,Object> beanMap = new HashMap<String ,Object>();
    public DispatcherServlet() {
    }
    /**
     * 本方法主要是获取获取xml文件信息，存放在Map中
     * @throws ServletException
     */
    public void init() throws ServletException {
        super.init();
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
                    String id = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class controllerBaneClass = Class.forName(className); //
                    Object bean = controllerBaneClass.newInstance();
                    beanMap.put(id, bean);
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException | ClassNotFoundException |
                 IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 在控制层处理发过来的请求
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //处理Map中的数据
        String servletPath = request.getServletPath();//获取request的ServletPath
        servletPath = servletPath.substring(1);//去掉第一个/
        int lastIndex = servletPath.lastIndexOf(".do");
        servletPath= servletPath.substring(0,lastIndex);//把.do去掉
        Object controllerBeanObj = beanMap.get(servletPath);//value去出来

        String operate = request.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }
        //通过反射技术获取该类的方法
        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method:methods) {
                if (operate.equals(method.getName())) {
                    //1、获取方法的参数
                    Parameter[] parameters = method.getParameters();
                    Object[] parametersValue = new Object[parameters.length]; //存放传过来的值
                    for (int i = 0; i < parameters.length; i++) {//
                        Parameter parameter = parameters[i];//从数组中拿出来
                        String parameterName = parameter.getName();
                        if("request".equals(parameterName)){//处理不是请求的数据
                            parametersValue[i] = request;
                        }else if("response".equals(parameterName)){
                            parametersValue[i]= response;
                        }else if("session".equals(parameterName)){
                            parametersValue[i] = request.getSession();
                        }else {
                            //从请求中获取主数据
                            String parameterValue = request.getParameter(parameterName);
                            String typeName = parameter.getType().getName();//获取是什么类型
                            Object parameterObj = parameterValue;
                            if (parameterObj != null){
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }
                            parametersValue[i] = parameterObj;
                        }
                    }
                    //2、controller组件调用
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj,parametersValue);//返回的跳转标志
                    //3、试图处理
                    String methodReturnStr = (String)returnObj;
                    if (methodReturnStr.startsWith("redirect")){
                        String redirectStr = methodReturnStr.substring("redirect:".length());//redirect:fruit.do
                        response.sendRedirect(redirectStr);//不进行页面跳转
                    }else {
                        super.processTemplate(methodReturnStr,request,response);//edit,index (跳转到相应的页面)
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
