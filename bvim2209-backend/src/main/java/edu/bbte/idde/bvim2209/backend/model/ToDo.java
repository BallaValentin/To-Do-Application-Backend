package edu.bbte.idde.bvim2209.backend.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=true)
public class ToDo extends BaseEntity {

    private String title;
    private String description;
    private Date dueDate;
    private Integer levelOfImportance;

    public ToDo() {
        super();
    }

    public ToDo(Long id, String title, String description, Date dueDate, Integer levelOfImportance) {
        super(id);
        this.title = title;
        this.description = description;
        this.dueDate = new Date(dueDate.getTime());
        this.levelOfImportance = levelOfImportance;
    }

    public Date getDueDate() {
        return new Date(dueDate.getTime());
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = new Date(dueDate.getTime());
    }

}


