package edu.bbte.idde.bvim2209.spring.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Table(name = "ToDo")
public class ToDo extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date dueDate;
    @Column(nullable = false)
    private Integer levelOfImportance;

    public Date getDueDate() {
        return new Date(dueDate.getTime());
    }

}


