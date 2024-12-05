package jCore.domain.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jCore.domain.user.model.entity.User;

import java.util.HashMap;
import java.util.Optional;

public interface UserRepositoryQ {
    Page<User> findUsersWithPageable(String query, Pageable pageable);
    Page<User> userGrid(HashMap<String, Object> mapParam, Pageable pageable);
    Optional<User> getMyInfo(String userCd);
}
