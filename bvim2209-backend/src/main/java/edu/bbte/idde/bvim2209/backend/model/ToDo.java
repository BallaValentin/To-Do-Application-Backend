package edu.bbte.idde.bvim2209.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ToDo extends BaseEntity {

    private String title;
    private String description;
    private Date dueDate;
    private Integer levelOfImportance;
}


