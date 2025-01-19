package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.backend.specification.ToDoDetailSpecification;
import edu.bbte.idde.bvim2209.spring.web.dto.request.ToDoDetailRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.ToDoDetailResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoDetailMapper;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/todos")
public class ToDoDetailController {
    ToDoService toDoService;
    ToDoMapper toDoMapper;
    ToDoDetailMapper toDoDetailMapper;

    @Autowired
    public ToDoDetailController(
            ToDoMapper toDoMapper, ToDoDetailMapper toDoDetailMapper, ToDoService toDoService) {
        this.toDoMapper = toDoMapper;
        this.toDoDetailMapper = toDoDetailMapper;
        this.toDoService = toDoService;
    }

    @GetMapping("/{id}/details")
    public Collection<ToDoDetailResponseDTO> getToDetails(
            @PathVariable Long id,
            @RequestParam(required = false) String text
    ) {
        ToDo toDo = toDoService.getById(id);
        Specification<ToDoDetail> specification = Specification.where(
                ToDoDetailSpecification.withParent(toDo))
                .and(ToDoDetailSpecification.withText(text));

        return toDoMapper.detailsToResponseDTOs(toDoService.getDetails(specification));
    }

    @PostMapping("/{id}/details")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ToDoDetailResponseDTO> createToDoDetailDTO(
            @PathVariable Long id, @Valid @RequestBody ToDoDetailRequestDTO toDoDetailRequestDTO) {
        ToDoDetail toDoDetail = toDoDetailMapper.requestDTOToModel(toDoDetailRequestDTO);
        toDoService.addDetailToToDo(id, toDoDetail);
        URI createURI = URI.create("api/todos/" + id + "/details/" + toDoDetail.getId());
        return ResponseEntity.created(createURI).body(
                toDoDetailMapper.modelToResponseDTO(toDoDetail)
        );
    }

    @DeleteMapping("/{todo-id}/details/{todo-detail-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToDoDetail(
            @PathVariable("todo-id") Long toDoId, @PathVariable("todo-detail-id") Long toDoDetailId) {
        toDoService.deleteDetailById(toDoId, toDoDetailId);
    }
}
