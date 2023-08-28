package com.atguigu.fruit.servlets;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.dao.myspringmvc.ViewBaseServlet;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.fruit.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.atguigu.fruit.util.StringUtil.isEmpty;
import static com.atguigu.fruit.util.StringUtil.isNotEmpty;


@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    FruitDAOImpl fruitDAO = new FruitDAOImpl();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String operate = request.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }
        switch (operate) {
            case "index":
                index(request, response);
                break;
            case "add":
                add(request, response);
                break;
            case "edit":
                edit(request, response);
                break;
            case "del":
                del(request, response);
                break;
            case "upDate":
                upDate(request, response);
                break;
            default:
                throw new  RuntimeException("Unknown operation");
        }
    }

    private void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNo = 1;
        String keyword = null;
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitlist;
        HttpSession session = request.getSession();
        if (isNotEmpty(request.getParameter("oper")) && "search".equals(request.getParameter("oper"))){
            pageNo = 1;
            keyword = request.getParameter("keyword");
            if (isEmpty(keyword)) {
                keyword = "";
            }
        }else {
            String pageNoStr = request.getParameter("pageNo");
            if (isNotEmpty(pageNoStr)) { //传过来的不是oper的话，正常显示
                pageNo = Integer.parseInt(pageNoStr);
            }
            Object keywordObj = session.getAttribute("keyword"); //把keyword存入session中
            if(keywordObj!=null){
                keyword = (String)keywordObj ;
            }else{
                keyword = "" ;
            }
        }
        session.setAttribute("pageNo", pageNo);
        fruitlist =  fruitDAO.getFruitPageNo(pageNo,keyword);//获取数据库分页结果
        session.setAttribute("fruitlist", fruitlist);
        int pageCount = fruitDAO.getFruitCount(keyword);
        pageCount = (pageCount+4)/5;
        session.setAttribute("pageCount", pageCount); //获取数据库统计结果
        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        super.processTemplate("index",request,response); //模版
    }
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fname = request.getParameter("fname");
        int price = Integer.parseInt(request.getParameter("price"));
        int fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");
        Fruit fruit = new Fruit(0,fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);//更新到sql去
        response.sendRedirect("fruit.do");//更新到index去
    }
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fidStr = request.getParameter("fid");
        if (isNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            fruitDAO.deleteFruit(fid);
            response.sendRedirect("fruit.do");
        }
    }
    private void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fid = request.getParameter("fid");
        if (isNotEmpty(fid)) {
            int fidInt = Integer.parseInt(fid);
            Fruit fruit = new FruitDAOImpl().getFruitById(fidInt);
            request.setAttribute("fruit", fruit);
            super.processTemplate("edit",request,response);
        }
    }
    private void upDate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        int fid = Integer.parseInt(request.getParameter("fid"));
        String fname = request.getParameter("fname");
        int price = Integer.parseInt(request.getParameter("price"));
        int fcount = Integer.parseInt(request.getParameter("fcount"));
        String remark = request.getParameter("remark");
        Fruit fruit = new Fruit(fid,fname,price,fcount,remark);
        fruitDAO.updateFruit(fruit);
        //资源跳转,并重新获取数据
        response.sendRedirect("fruit.do");
    }
}
