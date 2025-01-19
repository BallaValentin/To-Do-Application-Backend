package edu.bbte.idde.bvim2209.spring.backend.specification;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import org.springframework.data.jpa.domain.Specification;

public class ToDoDetailSpecification {
    public static Specification<ToDoDetail> withText(String text) {
        return (root, query, criteriaBuilder) ->
                text != null ? criteriaBuilder.equal(root.get("text"), text) : null;
    }

    public static Specification<ToDoDetail> withParent(ToDo toDo) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("toDo"), toDo);
    }
}
