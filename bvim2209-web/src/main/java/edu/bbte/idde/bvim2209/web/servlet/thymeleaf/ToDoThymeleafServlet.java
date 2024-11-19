package edu.bbte.idde.bvim2209.web.servlet.thymeleaf;

import edu.bbte.idde.bvim2209.backend.model.ToDo;
import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/todo/*")
public class ToDoThymeleafServlet extends HttpServlet {
    private final transient ToDoServiceImpl toDoService = new ToDoServiceImpl();

    @Override
    public void init() throws ServletException {
        super.init();
        ThymeleafEngineFactory.buildTemplateEngine(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> model = new ConcurrentHashMap<>();
        Collection<ToDo> toDoCollection = toDoService.findAll();
        model.put("todos", toDoCollection);
        ThymeleafEngineFactory.process(req, resp, "todos.html", model);
    }
}
