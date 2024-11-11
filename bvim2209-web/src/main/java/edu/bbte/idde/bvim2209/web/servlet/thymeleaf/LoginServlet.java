package edu.bbte.idde.bvim2209.web.servlet.thymeleaf;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        ThymeleafEngineFactory.buildTemplateEngine(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> model = new ConcurrentHashMap<>();
        ThymeleafEngineFactory.process(req, resp, "login.html", model);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String enteredUsername = req.getParameter("username");
        String enteredPassword = req.getParameter("password");
        if ("admin".equals(enteredUsername) && "12345".equals(enteredPassword)) {
            logger.info("The entered username and password are correct");
            logger.info("Redirecting to todos page");
            req.getSession().setAttribute("loggedIn", true);
            resp.sendRedirect(req.getContextPath() + "/todo");
        } else {
            logger.warn("Invalid username or password");
            resp.sendRedirect(req.getContextPath() + "/login?error=true");
        }
    }
}
