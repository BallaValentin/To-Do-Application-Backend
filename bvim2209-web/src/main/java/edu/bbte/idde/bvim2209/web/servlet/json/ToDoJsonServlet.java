package edu.bbte.idde.bvim2209.web.servlet.json;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.ToDo;

import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImpl;
import edu.bbte.idde.bvim2209.web.servlet.messages.HttpErrorMessage;
import edu.bbte.idde.bvim2209.web.servlet.messages.HttpSuccessMessage;
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
    private static final ObjectMapper objectMapper = JsonConfig.createConfiguredObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ToDoJsonServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        logger.info("Executing doGet request");

        String id = req.getParameter("id");

        Integer priority =
                req.getParameter("levelOfImportance") == null
                        ? null : Integer.parseInt(req.getParameter("levelOfImportance"));

        if (id == null) {
            logger.info("id is null, preparing to find all todo`s");
            Collection<ToDo> toDoCollection;
            if (priority != null) {
                toDoCollection = toDoService.findAllByPriority(priority);
            } else {
                toDoCollection = toDoService.findAll();
            }
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
                    handleNotFound(resp, "To do not found");
                }
            } catch (IllegalArgumentException exception) {
                logger.warn("Invalid id: {} in query", id);
                handleBadRequest(resp, exception.getMessage());
            } catch (EntityNotFoundException exception) {
                logger.warn("Entity with id: {} not found", id);
                handleNotFound(resp, exception.getMessage());
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
                logger.warn("The 'id' field should not be provided.");
                handleBadRequest(resp, "The 'id' field should not be provided.");
                return;
            }
            logger.info("Inserting new todo.");
            toDoService.createToDo(toDo);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            HttpSuccessMessage successMessage = new HttpSuccessMessage("Entity created successfully");
            resp.getWriter().write(objectMapper.writeValueAsString(successMessage));
        } catch (IllegalArgumentException | JsonMappingException exception) {
            logger.warn("Invalid request", exception);
            handleBadRequest(resp, exception.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        logger.info("Executing doPut request");
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                logger.warn("The 'id' field should be provided.");
                handleEmptyID(resp);
                return;
            }
            Long id = Long.parseLong(idParam);
            ToDo toDo = objectMapper.readValue(req.getReader(), ToDo.class);
            if (toDo.getId() != null) {
                logger.warn("The 'id' field should only be provided as parameter");
                handleBadRequest(resp,
                        "The 'id' field should only be provided as a parameter.");
                return;
            }
            toDo.setId(id);
            toDoService.updateToDo(toDo);
            HttpSuccessMessage successMessage = new HttpSuccessMessage("Entity updated successfully");
            resp.getWriter().write(objectMapper.writeValueAsString(successMessage));
        } catch (EntityNotFoundException exception) {
            logger.warn("Failed to update entity: {}", exception.getMessage());
            handleNotFound(resp, exception.getMessage());
        } catch (IllegalArgumentException exception) {
            logger.warn("The provided id could not be parsed to long");
            handleBadRequest(resp, exception.getMessage());
        } catch (JsonMappingException exception) {
            logger.warn("Failed to map ToDo {}", exception.getMessage());
            handleBadRequest(resp, exception.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        logger.info("Executing doDelete request");
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isBlank()) {
                handleEmptyID(resp);
                return;
            }
            Long id = Long.parseLong(idParam);
            toDoService.deleteToDo(id);
            HttpSuccessMessage successMessage = new HttpSuccessMessage("Entity deleted successfully");
            resp.getWriter().write(objectMapper.writeValueAsString(successMessage));
        } catch (IllegalArgumentException exception) {
            logger.warn("Invalid request", exception);
            handleBadRequest(resp, exception.getMessage());
        } catch (EntityNotFoundException exception) {
            logger.warn("Failed to delete entity: {}", exception.getMessage());
            handleNotFound(resp, exception.getMessage());
        }
    }

    private static void handleEmptyID(HttpServletResponse resp) {
        logger.warn("Id has not been provided.");
        handleBadRequest(resp, "The id field should be provided.");
    }

    private static void handleBadRequest(HttpServletResponse resp, String message) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        HttpErrorMessage errorMessage = new HttpErrorMessage(message);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(errorMessage));
        } catch (IOException ioException) {
            logger.error("Failed to write error response", ioException);
        }
    }

    private static void handleNotFound(HttpServletResponse resp, String message) {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        HttpErrorMessage errorMessage = new HttpErrorMessage(message);
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(objectMapper.writeValueAsString(errorMessage));
        } catch (IOException ioException) {
            logger.error("Failed to write error response", ioException);
        }
    }
}
