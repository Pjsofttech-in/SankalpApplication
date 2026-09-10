package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.Marquee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarqueeRepository extends JpaRepository<Marquee, Long> {
}