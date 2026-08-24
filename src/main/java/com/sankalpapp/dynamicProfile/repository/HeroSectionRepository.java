package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.HeroSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroSectionRepository extends JpaRepository<HeroSection, Long> {
}