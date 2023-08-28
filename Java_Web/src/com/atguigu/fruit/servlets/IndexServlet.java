package com.atguigu.fruit.servlets;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.dao.myspringmvc.ViewBaseServlet;
import com.atguigu.fruit.pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.atguigu.fruit.util.StringUtil.isNotEmpty;

/**
 * 调用访问
 */
@WebServlet("/index")//Servlet3.0开始支持注解
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNo = 1;
        FruitDAO fruitDAO = new FruitDAOImpl();

        List<Fruit> fruitlist;
        HttpSession  session = request.getSession();
        String pageNoStr = request.getParameter("pageNo");
        if (isNotEmpty(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }
        session.setAttribute("pageNo", pageNo);
        fruitlist =  fruitDAO.getFruitPageNo(pageNo);
        session.setAttribute("fruitlist", fruitlist);
        int pageCount = fruitDAO.getFruitCount();
        pageCount = (pageCount+4)/5;
        session.setAttribute("pageCount", pageCount); //获取数据库统计结果
        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        super.processTemplate("index",request,response); //模版
    }
}
