package edu.bbte.idde.bvim2209.spring.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.Date;

@Data
public class ToDoRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "DueDate cannot be null")
    private Date dueDate;

    @NotNull(message = "LevelOfImportance cannot be null")
    @Positive(message = "LevelOfImportance level must be positive")
    private Integer levelOfImportance;
}
