package edu.bbte.idde.bvim2209.web.servlet.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bvim2209.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.backend.services.ToDoServiceImpl;
import edu.bbte.idde.bvim2209.web.servlet.DTO.StatDTO;
import edu.bbte.idde.bvim2209.web.servlet.messages.HttpErrorMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/stat")
public class QueryStatServlet extends HttpServlet {
    private static final ToDoService toDoService = new ToDoServiceImpl();
    private static final ObjectMapper objectMapper = JsonConfig.createConfiguredObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        Integer logQueriesCount = toDoService.getLogQueryCount();
        Integer logUpdatesCount = toDoService.getLogUpdateCount();

        if (logQueriesCount == null && logUpdatesCount == null) {
            HttpErrorMessage errorMessage = new HttpErrorMessage("Logs are set to false");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(objectMapper.writeValueAsString(errorMessage));
        } else {
            StatDTO statDTO = new StatDTO();
            statDTO.setLogQueriesNumber(logQueriesCount);
            statDTO.setLogUpdatesNumber(logUpdatesCount);
            objectMapper.writeValue(resp.getWriter(), statDTO);
        }
    }
}
