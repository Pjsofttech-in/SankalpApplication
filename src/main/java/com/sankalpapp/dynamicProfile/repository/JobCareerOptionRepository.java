package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebJobCareerOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobCareerOptionRepository extends JpaRepository<WebJobCareerOption, Long> {

    @Query("SELECT j FROM WebJobCareerOption j ORDER BY j.id DESC")
    List<WebJobCareerOption> findAllOrderById();
}