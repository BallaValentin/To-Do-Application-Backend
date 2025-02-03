package edu.bbte.idde.bvim2209.spring.web.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
