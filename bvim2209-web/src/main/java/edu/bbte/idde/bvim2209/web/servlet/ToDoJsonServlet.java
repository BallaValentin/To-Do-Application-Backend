package edu.bbte.idde.bvim2209.web.servlet;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.ToDo;

import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Optional;

@WebServlet("/todos")
public class ToDoJsonServlet extends HttpServlet {
    private final ToDoServiceImpl toDoService = new ToDoServiceImpl();
    private final ObjectMapper objectMapper = JsonConfig.createConfiguredObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ToDoJsonServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
                    logger.error("Failed to write error response", ioException);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            ToDo toDo = objectMapper.readValue(req.getReader(), ToDo.class);
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
                logger.error("Failed to write error response", ioException);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try {
            String idParam = req.getParameter("id");
            if (idParam == null) {
                throw new IllegalArgumentException("The 'id' field should be provided.");
            }
            Long id = Long.parseLong(idParam);
            ToDo toDo = objectMapper.readValue(req.getReader(), ToDo.class);
            if (toDo.getId() != null) {
                throw new IllegalArgumentException(
                        "The 'id' field should only be provided as a parameter.");
            }
            toDo.setId(id);
            toDoService.updateToDo(toDo);
            resp.getWriter().write("{\"message\": \"Entity with id '" + id + "' updated successfully.\"}");
        } catch (EntityNotFoundException | IllegalArgumentException exception) {
            if (exception instanceof EntityNotFoundException) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            try (PrintWriter writer = resp.getWriter()) {
                writer.write("{\"error\": \"" + exception.getMessage() + "\"}");
            } catch (IOException ioException) {
                logger.error("Failed to write error response", ioException);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        super.doDelete(req, resp);
    }
}
