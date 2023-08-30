package com.atguigu.myssm.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.atguigu.myssm.util.StringUtil.isNotEmpty;

/**
 * 拦截器实现类
 */
@WebFilter(urlPatterns = {"*.do"},initParams = {@WebInitParam(name = "encoding",value = "utf-8")})
public class CharacterEncoding implements Filter {
    private String encoding = "UTF-8";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String encoding = filterConfig.getInitParameter("encoding");
        if (isNotEmpty(encoding)) {
            this.encoding = encoding;
        }
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletRequest)servletRequest).setCharacterEncoding(encoding);
        filterChain.doFilter(servletRequest,servletResponse);
    }
    @Override
    public void destroy() {

    }
}
