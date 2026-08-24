package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
}