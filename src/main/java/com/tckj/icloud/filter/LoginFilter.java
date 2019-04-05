package com.tckj.icloud.filter;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * session过期之后重新登录功能
 */
@Order(1)
@WebFilter(filterName = "loginFilter", urlPatterns = {"/*"})
public class LoginFilter implements Filter {
    String NO_LOGIN = "您还未登录";
    /**
     * 不需要限制的路径
     */
    private String[] includeUrls = new String[]{"/"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();
        //是否需要过滤
        boolean needFilter = isNeedFilter(uri);
        if (!needFilter) { //不需要过滤直接传给下一个过滤器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { //需要过滤器
            //3个月有效期
            // session中包含user对象,则是登录状态
            if (session != null && session.getAttribute("userName") != null) {
                filterChain.doFilter(request, response);
            } else {
                String requestType = request.getHeader("X-Requested-With");
                //判断是否是ajax请求
                if (requestType != null && "XMLHttpRequest".equals(requestType)) {
                    response.getWriter().write(this.NO_LOGIN);
                } else {
                    //重定向到登录页(需要在static文件夹下建立此html文件)
                    response.sendRedirect(request.getContextPath() + "/");
                }
                return;
            }
        }

    }


    @Override
    public void destroy() {

    }

    /**
     * 是否需要过滤
     *
     * @param uri
     * @return
     */
    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if (includeUrl.equals(uri)) {
                return false;
            }
        }

        return true;
    }


}
