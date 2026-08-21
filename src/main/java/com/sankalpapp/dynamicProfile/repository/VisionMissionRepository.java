package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebVisionMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisionMissionRepository extends JpaRepository<WebVisionMission, Long> {

    @Query("SELECT v FROM WebVisionMission v ORDER BY v.id DESC")
    List<WebVisionMission> findAllOrderById();

}