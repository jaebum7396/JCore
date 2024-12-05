package jCore.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jCore.domain.user.model.entity.UserInfo;
import jCore.domain.user.model.entity.UserProfileImage;
@Repository
public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, UserInfo> {

}
