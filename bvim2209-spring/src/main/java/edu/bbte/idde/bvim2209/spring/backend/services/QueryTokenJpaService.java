package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.config.CustomConfig;
import edu.bbte.idde.bvim2209.spring.backend.model.QueryToken;
import edu.bbte.idde.bvim2209.spring.backend.repo.jpa.QueryTokenJpaRepository;
import edu.bbte.idde.bvim2209.spring.exceptions.UnauthorizedException;
import edu.bbte.idde.bvim2209.spring.web.exception.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("jpa")
public class QueryTokenJpaService {
    public final QueryTokenJpaRepository queryTokenJpaRepository;

    @Autowired
    public QueryTokenJpaService(QueryTokenJpaRepository queryTokenJpaRepository,
                                CustomConfig customConfig) {
        this.queryTokenJpaRepository = queryTokenJpaRepository;
        Boolean isInsertQueryTokenAllowed = customConfig.getEnableInsertQueryTokens();
        if (isInsertQueryTokenAllowed) {
            queryTokenJpaRepository.save(
                    new QueryToken("ABCD", "READ", "TODO"));
            queryTokenJpaRepository.save(
                    new QueryToken("EFGH", "WRITE", "TODO"));
        }
    }

    public void checkToken(String token, String operationType, String entityName) {
        QueryToken queryToken = queryTokenJpaRepository.findByToken(token);
        if (queryToken != null) {
            if (!queryToken.getOperationType().equals(operationType)
                    || !queryToken.getEntityName().equals(entityName)) {
                throw new ForbiddenException("Invalid token for this operation");
            }
        } else {
            throw new UnauthorizedException("Invalid jwt token");
        }
    }
}
