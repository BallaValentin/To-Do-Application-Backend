package edu.bbte.idde.bvim2209.spring.backend.specification;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ToDoSpecification {
    public static Specification<ToDo> withPriority(Integer priority) {
        return (root, query, criteriaBuilder) ->
                priority != null ? criteriaBuilder.equal(root.get("priority"), priority) : null;
    }

    public static Specification<ToDo> withDueDateBefore(Date dueDate) {
        return (root, query, criteriaBuilder) ->
                dueDate != null
                        ? criteriaBuilder.greaterThanOrEqualTo(root.get("dueDate"), dueDate) : null;
    }

    public static Specification<ToDo> withDueDateAfter(Date dueDate) {
        return (root, query, criteriaBuilder) ->
                dueDate != null
                        ? criteriaBuilder.lessThanOrEqualTo(root.get("dueDate"), dueDate) : null;
    }
}
