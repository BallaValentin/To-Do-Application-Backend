package edu.bbte.idde.bvim2209.web.servlet.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/todo")
public class AuthenticationFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void init() {
        logger.info("Initializing AuthenticationFilter");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
        throws IOException, ServletException {
        logger.info("{} {}", req.getMethod(), req.getRequestURI());

        Boolean loggedIn = (Boolean) req.getSession().getAttribute("loggedIn");
        if (loggedIn != null && loggedIn) {
            logger.info("Already logged in, forwarding to todos page");
            chain.doFilter(req, resp);
        } else {
            logger.info("Not logged in, forwarding to login page");
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}
