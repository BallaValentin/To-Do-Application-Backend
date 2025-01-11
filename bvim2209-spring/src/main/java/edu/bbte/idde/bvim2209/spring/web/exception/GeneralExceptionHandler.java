package edu.bbte.idde.bvim2209.spring.web.exception;

import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.spring.exceptions.InvalidJwtException;
import edu.bbte.idde.bvim2209.spring.exceptions.UnauthorizedException;
import edu.bbte.idde.bvim2209.spring.web.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseDTO handleNotFound(
            EntityNotFoundException exception, HttpServletRequest request) {
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

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponseDTO handleBadCredentials(
            BadCredentialsException exception, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String error = exception.getMessage();
        String path = request.getRequestURI();
        return new ErrorResponseDTO(timestamp, statusCode, error, path);
    }

    @ExceptionHandler(InvalidJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponseDTO handleInvalidJwtException(
            InvalidJwtException exception, HttpServletRequest request
    ) {
        LocalDateTime timestamp = LocalDateTime.now();
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String error = exception.getMessage();
        String path = request.getRequestURI();
        return new ErrorResponseDTO(timestamp, statusCode, error, path);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponseDTO handleUnauthorizedException(
            UnauthorizedException exception, HttpServletRequest request
    ) {
        LocalDateTime timestamp = LocalDateTime.now();
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        String error = exception.getMessage();
        String path = request.getRequestURI();
        return new ErrorResponseDTO(timestamp, statusCode, error, path);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDTO handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpServletRequest request) {
        LocalDateTime timestamp = LocalDateTime.now();
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        String error = exception.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        String path = request.getRequestURI();
        return new ErrorResponseDTO(timestamp, statusCode, error, path);
    }
}
