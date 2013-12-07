package com.doufangbian.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckLogin implements Filter {

    public void destroy() {
        // TODO Auto-generated method stub

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        // 获取session
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getRequestURI();
        if (path.contains(".jsp")) {
            if (path.contains("login.jsp")) {
                filterChain.doFilter(request, response);// 继续
                return;
            } else if (request.getSession().getAttribute("userInfo") == null) {
                // 踢出去
                String basePath = request.getScheme() + "://"
                        + request.getServerName() + ":"
                        + request.getServerPort() + request.getContextPath()
                        + "/";
                //				response.sendRedirect(basePath + "admin/login.jsp");
                response.getWriter().print("<script type='text/javascript'>alert('您还未登录，或者session已过期，请重新登录！');window.parent.location='" + basePath + "admin/login.jsp'</script>");
            }
        }
        filterChain.doFilter(request, response);

    }

    public void init(FilterConfig arg0) throws ServletException {
        // TODO Auto-generated method stub

    }

}
