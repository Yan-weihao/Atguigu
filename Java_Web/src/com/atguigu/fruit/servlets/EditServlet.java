package com.atguigu.fruit.servlets;

import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.dao.myspringmvc.ViewBaseServlet;
import com.atguigu.fruit.pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;

import static com.atguigu.fruit.util.StringUtil.isNotEmpty;


//处理编辑页面，响应edit界面，
@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet  {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fid = request.getParameter("fid");
        if (isNotEmpty(fid)) {
            int fidInt = Integer.parseInt(fid);
            Fruit fruit = new FruitDAOImpl().getFruitById(fidInt);
            request.setAttribute("fruit", fruit);
            super.processTemplate("edit",request,response);
        }
    }
}
