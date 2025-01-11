package edu.bbte.idde.bvim2209.spring.web.dto.response;

import lombok.Data;

import java.util.Collection;
import java.util.Date;

@Data
public class ToDoResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Date dueDate;
    private Integer levelOfImportance;
    private String createdBy;
    private Collection<ToDoDetailResponseDTO> details;
}
