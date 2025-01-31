package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.Query;
import edu.bbte.idde.bvim2209.spring.backend.repo.jpa.QueryJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class QueryService {
    public final QueryJpaRepository queryJpaRepository;

    @Autowired
    public QueryService(QueryJpaRepository queryJpaRepository) {
        this.queryJpaRepository = queryJpaRepository;
    }

    public Collection<Query> findAll(Specification<Query> spec) {
        return queryJpaRepository.findAll(spec);
    }
}
