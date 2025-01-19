package edu.bbte.idde.bvim2209.spring.backend.specification;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ToDoSpecification {
    public static Specification<ToDo> withPriority(Integer levelOfImportance) {
        return (root, query, criteriaBuilder) ->
                levelOfImportance != null ? criteriaBuilder.equal(root.get("levelOfImportance"),
                        levelOfImportance) : null;
    }

    public static Specification<ToDo> withTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title != null ? criteriaBuilder.equal(root.get("title"), title) : null;
    }

    public static Specification<ToDo> withDescription(String description) {
        return (root, query, criteriaBuilder) ->
                description != null ? criteriaBuilder.equal(root.get("description"), description) : null;
    }

    public static Specification<ToDo> withDueDateBefore(Date date) {
        return (root, query, criteriaBuilder) ->
                date != null ? criteriaBuilder.lessThanOrEqualTo(root.get("dueDate"), date) : null;
    }

    public static Specification<ToDo> withDueDateAfter(Date date) {
        return (root, query, criteriaBuilder) ->
                date != null ? criteriaBuilder.greaterThanOrEqualTo(root.get("dueDate"), date) : null;
    }
}
