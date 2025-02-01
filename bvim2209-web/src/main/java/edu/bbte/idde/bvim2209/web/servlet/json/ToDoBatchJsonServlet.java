package edu.bbte.idde.bvim2209.web.servlet.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bvim2209.backend.model.ToDo;
import edu.bbte.idde.bvim2209.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImpl;
import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImplFactory;
import edu.bbte.idde.bvim2209.web.servlet.dto.ToDoListDTO;
import edu.bbte.idde.bvim2209.web.servlet.messages.HttpErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/todos/batch")
@Slf4j
public class ToDoBatchJsonServlet extends HttpServlet {
    ToDoService toDoService = ToDoServiceImplFactory.getInstance();
    ObjectMapper objectMapper = JsonConfig.createConfiguredObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        ToDoListDTO toDoListDTO = objectMapper.readValue(req.getReader(), ToDoListDTO.class);
        Collection<ToDo> toDos = toDoListDTO.getTodos();

        try {
            toDoService.addTodos(toDos);
        } catch (IllegalArgumentException exception) {
            HttpErrorMessage errorMessage = new HttpErrorMessage("Failed to add todos");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(objectMapper.writeValueAsString(errorMessage));
        }
    }
}
