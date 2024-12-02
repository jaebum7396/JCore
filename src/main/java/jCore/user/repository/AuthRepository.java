package jCore.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jCore.user.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User,String> {
    Optional<User> findByUserCd(String userCd);
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserNm(String userNm);
    List<User> findAll();
}