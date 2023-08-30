package com.atguigu.myssm;

import com.atguigu.myssm.util.StringUtil;
import com.atguigu.myssm.ioc.BeanFactory;
import com.atguigu.myssm.ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 控制器
 */
@WebServlet("*.do") //拦截全部.do后缀
public class DispatcherServlet extends ViewBaseServlet{
    private BeanFactory beanFactory ;

    public DispatcherServlet() {
    }
    /**
     * 本方法主要是获取获取xml文件信息，存放在Map中
     * @throws ServletException
     */
    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();

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
        //处理Map中的数据
        String servletPath = request.getServletPath();//获取request的ServletPath
        servletPath = servletPath.substring(1);//去掉第一个/
        int lastIndex = servletPath.lastIndexOf(".do");
        servletPath= servletPath.substring(0,lastIndex);//把.do去掉
        Object controllerBeanObj = beanFactory.getBean(servletPath);//value去出来

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
