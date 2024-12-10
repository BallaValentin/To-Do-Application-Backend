package edu.bbte.idde.bvim2209.spring.web.exception;

import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.spring.web.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDTO handleNotFound(
            EntityNotFoundException exception, HttpServletRequest request) {
        log.debug("Im here...");
        LocalDateTime timestamp = LocalDateTime.now();
        Integer statusCode = HttpStatus.NOT_FOUND.value();
        String error = exception.getMessage();
        String path = request.getRequestURI();
        return new ErrorResponseDTO(timestamp, statusCode, error, path);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDTO handleBadRequest(
            IllegalArgumentException exception, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String error = exception.getMessage();
        String path = request.getRequestURI();
        return new ErrorResponseDTO(timestamp, statusCode, error, path);
    }
}
