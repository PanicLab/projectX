package com.github.paniclab.filters;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Вход в CharsetFilter");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        System.out.println("CharsetFilter вызывает doFilter()");
        chain.doFilter(req, resp);
        System.out.println("Выход из CharsetFilter");
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
