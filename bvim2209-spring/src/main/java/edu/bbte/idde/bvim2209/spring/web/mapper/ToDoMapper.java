package edu.bbte.idde.bvim2209.spring.web.mapper;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.web.dto.request.ToDoRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.ToDoDetailResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.ToDoResponseDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public  abstract class ToDoMapper {
    @IterableMapping(elementTargetType = ToDoResponseDTO.class)
    public abstract Collection<ToDoResponseDTO> modelsToResponseDTO(Iterable<ToDo> model);

    public abstract ToDoResponseDTO modelToResponseDTO(ToDo model);

    public abstract ToDo requestDTOToModel(ToDoRequestDTO toDoDto);

    public abstract ToDoDetailResponseDTO detailToResponseDTO(ToDoDetail detail);

    @IterableMapping(elementTargetType = ToDoDetailResponseDTO.class)
    public abstract Collection<ToDoDetailResponseDTO> detailsToResponseDTOs(Collection<ToDoDetail> details);

}
