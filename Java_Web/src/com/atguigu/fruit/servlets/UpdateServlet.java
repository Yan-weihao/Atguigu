package com.atguigu.fruit.servlets;

import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.dao.myspringmvc.ViewBaseServlet;
import com.atguigu.fruit.pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {
    FruitDAOImpl fruitDAO = new FruitDAOImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        int fid = Integer.parseInt(request.getParameter("fid"));
        String fname = request.getParameter("fname");
        int price = Integer.parseInt(request.getParameter("price"));
        int fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");
        Fruit fruit = new Fruit(fid,fname,price,fcount,remark);
        fruitDAO.updateFruit(fruit);
        //资源跳转,并重新获取数据
        response.sendRedirect("index");
    }
}
