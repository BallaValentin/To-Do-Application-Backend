package edu.bbte.idde.bvim2209.web.servlet;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.ToDo;

import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Optional;

@WebServlet("/todos")
public class ToDoJsonServlet extends HttpServlet {
    private final ToDoServiceImpl toDoService = new ToDoServiceImpl();
    private final ObjectMapper objectMapper = JsonConfig.createConfiguredObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String id = req.getParameter("id");
        if (id == null) {
            Collection<ToDo> toDoCollection = toDoService.findAll();
            resp.getWriter().write(objectMapper.writeValueAsString(toDoCollection));
        } else {
            try {
                Optional<ToDo> toDo = Optional.ofNullable(toDoService.findById(Long.parseLong(id)));
                if (toDo.isPresent()) {
                    resp.getWriter().write(objectMapper.writeValueAsString(toDo.get()));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (EntityNotFoundException exception) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                try (PrintWriter writer = resp.getWriter()) {
                    writer.write("{\"error\": \"" + exception.getMessage() + "\"}");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            ToDo toDo = objectMapper.readValue(req.getReader(), ToDo.class);
            System.out.println(toDo.getId());
            if (toDo.getId() != null) {
                throw new IllegalArgumentException("The 'id' field should not be provided.");
            }
            toDoService.createToDo(toDo);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"message\": \"Entity created successfully.\"}");
        } catch (IllegalArgumentException | JsonMappingException exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter writer = resp.getWriter()) {
                writer.write("{\"error\": \"" + exception.getMessage() + "\"}");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        super.doDelete(req, resp);
    }
}
