package jCore.domain.user.repository;

import jCore.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jCore.domain.user.model.entity.UserInfo;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    Optional<UserInfo> findByUser(User user);
    Optional<UserInfo> findByUserInfoCd(String userInfoCd);
}