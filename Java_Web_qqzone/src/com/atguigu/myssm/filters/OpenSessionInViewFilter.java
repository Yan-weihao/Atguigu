package com.atguigu.myssm.filters;

import com.atguigu.myssm.trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 过滤器处理事务
 */
@WebFilter("*.do")
public class OpenSessionInViewFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            //开启事务
            TransactionManager.beginTrans();
            System.out.println("开启事务");

            filterChain.doFilter(servletRequest, servletResponse);
            //提交事务
            TransactionManager.commit();
            System.out.println("提交事务");

        }catch (Exception e) {
            e.printStackTrace();
            try {
                TransactionManager.rollback();
                //回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
