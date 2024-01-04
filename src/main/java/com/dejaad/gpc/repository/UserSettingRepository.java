package com.dejaad.gpc.repository;

import com.dejaad.gpc.model.entity.UserSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserSettingRepository extends JpaRepository<UserSettingEntity, String> {
    @Query("select us from UserSettingEntity us where us.user.id = ?1")
    Optional<UserSettingEntity> findByUser_Id(String userId);

}
