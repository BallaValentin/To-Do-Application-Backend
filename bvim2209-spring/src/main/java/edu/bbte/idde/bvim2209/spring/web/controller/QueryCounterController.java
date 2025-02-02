package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.QueryCounter;
import edu.bbte.idde.bvim2209.spring.backend.services.QueryCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queries")
public class QueryCounterController {
    QueryCounterService queryCounterService;

    @Autowired
    public QueryCounterController(QueryCounterService queryCounterService) {
        this.queryCounterService = queryCounterService;
    }

    @GetMapping
    public QueryCounter getQueryCounter(
            @RequestParam String queryType,
            @RequestParam String entityName
    ) {
        return queryCounterService.getQueryCounter(queryType, entityName);
    }
}
