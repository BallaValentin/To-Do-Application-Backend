package edu.bbte.idde.bvim2209.spring.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ToDo extends BaseEntity {

    private String title;
    private String description;
    private Date dueDate;
    private Integer levelOfImportance;

    public Date getDueDate() {
        return new Date(dueDate.getTime());
    }

    public void setDueDate(Date dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ToDoList{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", dueDate='").append(dueDate).append('\'');
        sb.append(", levelOfImportance=").append(levelOfImportance);
        sb.append('}');
        return sb.toString();
    }

}


