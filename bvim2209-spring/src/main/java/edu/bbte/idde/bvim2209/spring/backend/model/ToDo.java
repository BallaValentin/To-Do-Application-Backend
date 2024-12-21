package edu.bbte.idde.bvim2209.spring.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "db_todo")
public class ToDo extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Date dueDate;
    @Column(nullable = false)
    private Integer levelOfImportance;

    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Collection<ToDoDetail> details = new ArrayList<>();

    public Date getDueDate() {
        return new Date(dueDate.getTime());
    }

}


