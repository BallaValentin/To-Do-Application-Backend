package edu.bbte.idde.bvim2209.web.servlet.dto;

import edu.bbte.idde.bvim2209.backend.model.ToDo;
import lombok.Data;

import java.util.Collection;

@Data
public class ToDoListDTO {
    private Collection<ToDo> todos;
}
