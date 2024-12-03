package jCore.api.token.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import jCore.api.token.dto.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
}