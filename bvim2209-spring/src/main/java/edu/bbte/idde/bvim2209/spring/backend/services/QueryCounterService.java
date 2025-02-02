package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.QueryCounter;

public interface QueryCounterService {
    QueryCounter getQueryCounter(String queryType, String entityName);

    void updateQueryCount(String queryType, String entityName);
}
