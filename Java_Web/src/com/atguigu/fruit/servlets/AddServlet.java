package com.atguigu.fruit.servlets;

import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.dao.myspringmvc.ViewBaseServlet;
import com.atguigu.fruit.pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.atguigu.fruit.util.StringUtil.isEmpty;

@WebServlet("/add.do")
public class AddServlet extends ViewBaseServlet {
    FruitDAOImpl fruitDAO = new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String fname = request.getParameter("fname");
        int price = Integer.parseInt(request.getParameter("price"));
        int fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");
        Fruit fruit = new Fruit(0,fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);//更新到sql去
        response.sendRedirect("index");//更新到index去
    }
}
