package edu.bbte.idde.bvim2209.spring.backend.specification;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import org.springframework.data.jpa.domain.Specification;

public class ToDoSpecification {
    public static Specification<ToDo> hasPriority(Integer levelOfImportance) {
        return (root, query, criteriaBuilder) ->
                levelOfImportance != null ? criteriaBuilder.equal(root.get("levelOfImportance"),
                        levelOfImportance) : null;
    }
}
