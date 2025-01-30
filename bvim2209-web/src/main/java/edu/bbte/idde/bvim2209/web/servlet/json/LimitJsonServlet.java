package edu.bbte.idde.bvim2209.web.servlet.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImpl;
import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImplFactory;
import edu.bbte.idde.bvim2209.web.servlet.messages.HttpSuccessMessage;
import edu.bbte.idde.bvim2209.web.servlet.messages.PostLimitRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/limit")
public class LimitJsonServlet extends HttpServlet {
    public static final ToDoServiceImpl toDoServiceImpl = ToDoServiceImplFactory.getInstance();
    private static final ObjectMapper objectMapper = JsonConfig.createConfiguredObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Long limit = toDoServiceImpl.getLimit();
        HttpSuccessMessage successMessage = new HttpSuccessMessage("The limit is: " + limit);
        resp.getWriter().write(objectMapper.writeValueAsString(successMessage));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Long limit = objectMapper.readValue(req.getReader(), PostLimitRequest.class).getLimit();
        toDoServiceImpl.setLimit(limit);
        HttpSuccessMessage successMessage = new HttpSuccessMessage("The new limit is: " + limit);
        resp.getWriter().write(objectMapper.writeValueAsString(successMessage));
    }
}
