package jCore.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jCore.api.user.model.entity.UserInfo;
import jCore.api.user.model.entity.UserProfileImage;
@Repository
public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, UserInfo> {

}
