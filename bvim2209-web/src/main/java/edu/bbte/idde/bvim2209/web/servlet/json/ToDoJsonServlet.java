package edu.bbte.idde.bvim2209.web.servlet.json;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.ToDo;

import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImpl;
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
    private final transient ToDoServiceImpl toDoService = new ToDoServiceImpl();
    private final ObjectMapper objectMapper = JsonConfig.createConfiguredObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ToDoJsonServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        logger.info("Executing doGet request");

        String id = req.getParameter("id");
        if (id == null) {
            logger.info("id is null, preparing to find all todo`s");
            Collection<ToDo> toDoCollection = toDoService.findAll();
            resp.getWriter().write(objectMapper.writeValueAsString(toDoCollection));
        } else {
            try {
                logger.info("Preparing to find todo with id: {}", id);
                Optional<ToDo> toDo = Optional.ofNullable(toDoService.findById(Long.parseLong(id)));
                if (toDo.isPresent()) {
                    logger.info("Todo with id: {} found", id);
                    resp.getWriter().write(objectMapper.writeValueAsString(toDo.get()));
                } else {
                    logger.warn("To do with id: {} not found", id);
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    try (PrintWriter writer = resp.getWriter()) {
                        writer.write("{\"error\": \"To do not found\"}");
                    } catch (IOException ioException) {
                        logger.error("Failed to write error response", ioException);
                    }
                }
            } catch (IllegalArgumentException exception) {
                logger.warn("Invalid id: {} in query", id);
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                try (PrintWriter writer = resp.getWriter()) {
                    writer.write("{\"error\"" + exception.getMessage() + "\"}");
                } catch (IOException ioException) {
                    logger.error("Failed to write error response", ioException);
                }
            } catch (EntityNotFoundException exception) {
                logger.warn("Entity with id: {} not found", id);
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                try (PrintWriter writer = resp.getWriter()) {
                    writer.write("{\"error\"" + exception.getMessage() + "\"}");
                } catch (IOException ioException) {
                    logger.error("Failed to write error response", ioException);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        logger.info("Executing doPost request");
        try {
            ToDo toDo = objectMapper.readValue(req.getReader(), ToDo.class);
            if (toDo.getId() != null) {
                logger.warn("The `id` field should not be provided.");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                try (PrintWriter writer = resp.getWriter()) {
                    writer.write("{\"error\": \"The 'id' field should not be provided.\"}");
                } catch (IOException ioException) {
                    logger.error("Failed to write error response", ioException);
                }
            }
            logger.info("Inserting new todo.");
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
        logger.info("Executing doPut request");
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                handleEmptyID(idParam, resp);
                return;
            }
            Long id = Long.parseLong(idParam);
            ToDo toDo = objectMapper.readValue(req.getReader(), ToDo.class);
            if (toDo.getId() != null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                try (PrintWriter writer = resp.getWriter()) {
                    writer.write("{\"error\": \"The 'id' "
                            + "field should only be provided as a parameter.\"}");
                } catch (IOException ioException) {
                    logger.error("Failed to write error response", ioException);
                }
            }
            toDo.setId(id);
            toDoService.updateToDo(toDo);
            resp.getWriter().write("{\"message\": \"Entity with id '" + id + "' updated successfully.\"}");
        } catch (EntityNotFoundException exception) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            try (PrintWriter writer = resp.getWriter()) {
                writer.write("{\"error\": \"" + exception.getMessage() + "\"}");
            } catch (IOException ioException) {
                logger.error("Failed to write error response", ioException);
            }
        } catch (IllegalArgumentException exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter writer = resp.getWriter()) {
                writer.write("{\"error\": \"" + exception.getMessage() + "\"}");
            } catch (IOException ioException) {
                logger.error("Failed to write error response", ioException);
            }
        } catch (JsonMappingException exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter writer = resp.getWriter()) {
                writer.write("{\"error\": \"" + exception.getMessage() + "\"}");
            } catch (IOException ioException) {
                logger.error("Failed to write error response", ioException);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        logger.info("Executing doDelete request");
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isBlank()) {
                handleEmptyID(idParam, resp);
                return;
            }
            Long id = Long.parseLong(idParam);
            toDoService.deleteToDo(id);
            resp.getWriter().write("{\"message\": \"Entity with id '" + id + "' deleted successfully.\"}");
        } catch (IllegalArgumentException exception) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            try (PrintWriter writer = resp.getWriter()) {
                writer.write("{\"error\": \"" + exception.getMessage() + "\"}");
            } catch (IOException ioException) {
                logger.error("Failed to write error response", ioException);
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

    private static void handleEmptyID(String idParam, HttpServletResponse resp) {
        logger.warn("Id has not been provided.");
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("{\"error\": \"" + "The `id` field should be provided." + "\"}");
        } catch (IOException ioException) {
            logger.error("Failed to write error response", ioException);
        }
    }
}
