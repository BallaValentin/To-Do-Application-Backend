package edu.bbte.idde.bvim2209.spring.exceptions;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String message) {
        super(message);
    }
}
