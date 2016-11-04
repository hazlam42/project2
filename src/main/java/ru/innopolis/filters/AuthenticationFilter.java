package ru.innopolis.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter implementation and we are validating user session here
 */
@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private Logger logger = Logger.getLogger(AuthenticationFilter.class);

    public void init(FilterConfig fConfig) throws ServletException {
        logger.info("AuthenticationFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        logger.info("Requested Resource::"+uri);

        HttpSession session = req.getSession(false);

        if(session == null && !(uri.endsWith("html") || uri.endsWith("Login") || uri.endsWith("Register"))){
            logger.error("Unauthorized access request");
            res.sendRedirect("login.html");
        }else{
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }


    }

    public void destroy() {
        //close any resources here
    }

}