package edu.bbte.idde.bvim2209.spring.web.mapper;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.web.dto.request.ToDoDetailRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.ToDoDetailResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ToDoDetailMapper {
    public abstract ToDoDetail requestDTOToModel(ToDoDetailRequestDTO toDoDetailRequestDTO);
    public abstract ToDoDetailResponseDTO modelToResponseDTO(ToDoDetail toDoDetail);
}
