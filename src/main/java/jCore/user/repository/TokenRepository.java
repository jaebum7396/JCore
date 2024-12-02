package jCore.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import jCore.user.model.dto.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
}