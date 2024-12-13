package jcore.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jcore.domain.user.model.entity.UserInfo;
import jcore.domain.user.model.entity.UserProfileImage;
@Repository
public interface UserProfileImageRepository extends JpaRepository<UserProfileImage, UserInfo> {

}
