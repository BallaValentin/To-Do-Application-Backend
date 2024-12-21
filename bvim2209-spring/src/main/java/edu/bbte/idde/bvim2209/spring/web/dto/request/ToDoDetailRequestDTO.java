package edu.bbte.idde.bvim2209.spring.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ToDoDetailRequestDTO {
    @NotEmpty(message = "Text cannot be blank")
    private String text;
}
