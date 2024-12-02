package jCore.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jCore.user.model.entity.UserInfo;
import jCore.user.model.entity.UserProfileImage;
@Repository
public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, UserInfo> {

}
