package jcore.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import jcore.domain.user.model.entity.User;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String>, QuerydslPredicateExecutor<User>, UserRepositoryQ  {
    User save(User userEntity);
    Optional<User> findByUserId(String userId);
    List<User> findAll();
}