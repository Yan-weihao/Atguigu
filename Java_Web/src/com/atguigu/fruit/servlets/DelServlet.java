package com.atguigu.fruit.servlets;

import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.dao.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.atguigu.fruit.util.StringUtil.isNotEmpty;
@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {

    FruitDAOImpl fruitDAO = new FruitDAOImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            fruitDAO.deleteFruit(fid);
            response.sendRedirect("index");
        }
    }
}
