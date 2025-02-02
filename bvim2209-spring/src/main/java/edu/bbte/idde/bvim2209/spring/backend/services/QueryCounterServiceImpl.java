package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.QueryCounter;
import edu.bbte.idde.bvim2209.spring.backend.repo.jpa.QueryCounterJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QueryCounterServiceImpl implements QueryCounterService {
    private final QueryCounterJpaRepository queryCounterJpaRepository;

    public QueryCounterServiceImpl(QueryCounterJpaRepository queryCounterJpaRepository) {
        this.queryCounterJpaRepository = queryCounterJpaRepository;
    }

    public QueryCounter getQueryCounter(String queryType, String entityName) {
        Optional<QueryCounter> queryCounter =
                queryCounterJpaRepository.findByQueryTypeAndEntityName(queryType, entityName);
        return queryCounter.orElseGet(() -> new QueryCounter(queryType, entityName));
    }

    public void updateQueryCount(String queryType, String entityName) {
        Optional<QueryCounter> queryCounter =
                queryCounterJpaRepository.findByQueryTypeAndEntityName(queryType, entityName);
        QueryCounter counter;
        if (queryCounter.isPresent()) {
            counter = queryCounter.get();
            counter.setCount(counter.getCount() + 1);
        } else {
            counter = new QueryCounter(queryType, entityName);
            counter.setCount(1);
        }
        queryCounterJpaRepository.save(counter);
    }
}
