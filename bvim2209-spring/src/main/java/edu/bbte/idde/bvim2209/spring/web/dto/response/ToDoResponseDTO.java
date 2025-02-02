package edu.bbte.idde.bvim2209.spring.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
public class ToDoResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer levelOfImportance;
    private Collection<ToDoDetailResponseDTO> details;
}
