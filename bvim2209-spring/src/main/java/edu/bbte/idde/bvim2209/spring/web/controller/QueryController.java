package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.Query;
import edu.bbte.idde.bvim2209.spring.backend.services.QueryService;
import edu.bbte.idde.bvim2209.spring.backend.specification.QuerySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collection;

@RestController
@RequestMapping("/api/queries")
public class QueryController {
    private final QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping
    public Collection<Query> getQueries(
            @RequestParam(required = false) Instant beforeDate,
            @RequestParam(required = false) Instant afterDate
    ) {
        Specification<Query> querySpecification = Specification.where(
                QuerySpecification.hasSearchDateBefore(beforeDate)
        ).and(QuerySpecification.hasSearchDateAfter(afterDate));
        return queryService.findAll(querySpecification);
    }
}
