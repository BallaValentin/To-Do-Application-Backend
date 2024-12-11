package edu.bbte.idde.bvim2209.spring.web.mapper;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.web.dto.ToDoRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.ToDoResponseDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public  abstract class ToDoMapper {
    @IterableMapping(elementTargetType = ToDoResponseDTO.class)
    public abstract Collection<ToDoResponseDTO> modelsToResponseDTO(Iterable<ToDo> model);

    public abstract ToDoResponseDTO modelToResponseDTO(ToDo model);

    public abstract ToDo requestDTOToModel(ToDoRequestDTO toDoDto);

}
