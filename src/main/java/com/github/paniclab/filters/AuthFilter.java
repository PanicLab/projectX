package com.github.paniclab.filters;

import com.github.paniclab.models.Profile;

import javax.servlet.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getAnonymousLogger();
    public void destroy() {
        LOGGER.info("Auth-фильтр уничтожен.");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String password = req.getParameter("password");
        String message = firstName + lastName + password;
        //ISO-8859-1
        //Windows-1251
        //UTF-8
        //UTF-16
        //UTF-16BE

/*        byte[] bytes = message.getBytes("ISO-8859-1");
        message = new String(bytes, "UTF-8");*/
        LOGGER.info(message);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        LOGGER.info("Auth-фильтр создан успешно.");
    }
}
