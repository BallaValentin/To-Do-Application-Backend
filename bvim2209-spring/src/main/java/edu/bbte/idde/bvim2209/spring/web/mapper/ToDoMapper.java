package edu.bbte.idde.bvim2209.spring.web.mapper;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.web.dto.ToDoDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public  abstract class ToDoMapper {
    @IterableMapping(elementTargetType = ToDoDto.class)
    public abstract Collection<ToDoDto> modelsToDto(Iterable<ToDo> model);

    public abstract ToDoDto modelToDto(ToDo model);

    public abstract ToDo dtoToModel(ToDoDto toDoDto);

}
