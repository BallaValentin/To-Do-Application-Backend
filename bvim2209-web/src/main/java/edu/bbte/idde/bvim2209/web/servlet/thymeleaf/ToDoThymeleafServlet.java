package edu.bbte.idde.bvim2209.web.servlet.thymeleaf;

import edu.bbte.idde.bvim2209.backend.model.ToDo;
import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, Object> model = new ConcurrentHashMap<>();
        Collection<ToDo> toDoCollection = toDoService.findAll();
        List<Map<String, Object>> todosForView = new ArrayList<>();
        for (ToDo toDo : toDoCollection) {
            todosForView.add(formatToDoForView(toDo, dateFormat));
        }
        model.put("todos", todosForView);

        ThymeleafEngineFactory.process(req, resp, "todos.html", model);
    }

    private Map<String, Object> formatToDoForView(ToDo todo, SimpleDateFormat dateFormat) {
        Map<String, Object> formattedTodo = new ConcurrentHashMap<>();
        formattedTodo.put("id", todo.getId());
        formattedTodo.put("title", todo.getTitle());
        formattedTodo.put("description", todo.getDescription());
        formattedTodo.put("dueDate", dateFormat.format(todo.getDueDate())); // Format date as string
        formattedTodo.put("levelOfImportance", todo.getLevelOfImportance());
        return formattedTodo;
    }
}
