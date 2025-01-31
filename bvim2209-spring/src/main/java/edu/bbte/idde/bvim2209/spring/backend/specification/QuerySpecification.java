package edu.bbte.idde.bvim2209.spring.backend.specification;

import edu.bbte.idde.bvim2209.spring.backend.model.Query;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public class QuerySpecification {

    public static Specification<Query> hasSearchDateBefore(Instant instant) {
        return (root, query, criteriaBuilder) ->
                instant != null ?
                        criteriaBuilder.lessThanOrEqualTo(root.get("searchDate"), instant) : null;
    }

    public static Specification<Query> hasSearchDateAfter(Instant instant) {
        return (root, query, criteriaBuilder) ->
                instant != null ?
                        criteriaBuilder.greaterThanOrEqualTo(root.get("searchDate"), instant) : null;
    }
}
