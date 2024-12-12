package edu.bbte.idde.bvim2209.spring.web.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponseDTO {
    private LocalDateTime timestamp;
    private Integer statusCode;
    private String error;
    private String path;

    public ErrorResponseDTO(LocalDateTime timestamp, Integer statusCode, String error, String path) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.error = error;
        this.path = path;
    }
}
