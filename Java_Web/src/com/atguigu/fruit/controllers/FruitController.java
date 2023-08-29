package com.atguigu.fruit.controllers;

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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.atguigu.fruit.util.StringUtil.isEmpty;
import static com.atguigu.fruit.util.StringUtil.isNotEmpty;



public class FruitController {
    FruitDAOImpl fruitDAO = new FruitDAOImpl();

    protected void service(HttpServletRequest request){

    }
    private String index(String keyword, String oper, Integer pageNo ,HttpServletRequest request) {
        HttpSession session = request.getSession() ;
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitlist = new ArrayList<>();
        if (pageNo == null) {
            pageNo = 1;
        }
        if (isNotEmpty(request.getParameter("oper")) && "search".equals(request.getParameter("oper"))){
            pageNo = 1;
            keyword = request.getParameter("keyword");
            if (isEmpty(keyword)) {
                keyword = "";
            }
        }else {
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
        return "index";
    }

    private String add(String fname, Integer price, Integer fcount, String remark) {
        Fruit fruit = new Fruit(0,fname, price, fcount, remark);
        fruitDAO.addFruit(fruit);//更新到sql去
        return "redirect:fruit.do";
    }
    private String del(Integer fid) {
        if (fid != null) {
            fruitDAO.deleteFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }
    private String edit(Integer fid,HttpServletRequest request){
        if (fid != null) {
            Fruit fruit = new FruitDAOImpl().getFruitById(fid);
            request.setAttribute("fruit", fruit);
            return "edit";
        }
        return "error";
    }
    private String upDate(Integer fid,String fname, Integer price, Integer fcount, String remark) {
        Fruit fruit = new Fruit(fid,fname,price,fcount,remark);
        fruitDAO.updateFruit(fruit);
        //资源跳转,并重新获取数据
        return "redirect:fruit.do";
    }
}
