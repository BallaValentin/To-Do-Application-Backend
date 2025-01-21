package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.web.dto.request.ToDoDetailRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.ToDoDetailResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoDetailMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/todos")
public class ToDoDetailController {
    ToDoService toDoService;
    ToDoDetailMapper toDoDetailMapper;

    @Autowired
    public ToDoDetailController(
            ToDoDetailMapper toDoDetailMapper, ToDoService toDoService) {
        this.toDoDetailMapper = toDoDetailMapper;
        this.toDoService = toDoService;
    }

    @GetMapping("/{id}/details")
    public Page<ToDoDetailResponseDTO> getToDetails(
            @PathVariable Long id,
            @RequestParam(defaultValue = "git0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        Page<ToDoDetail> detailsPage = toDoService.getDetails(id, page, size, sortBy, order);
        return detailsPage.map(toDoDetailMapper::modelToResponseDTO);
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
