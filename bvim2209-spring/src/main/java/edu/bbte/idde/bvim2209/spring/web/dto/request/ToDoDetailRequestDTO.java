package edu.bbte.idde.bvim2209.spring.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ToDoDetailRequestDTO {
    @NotEmpty(message = "Text cannot be blank")
    @NotNull(message = "Text cannot be null")
    private String text;
}
