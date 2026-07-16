package com.testapplication.repository;

import com.testapplication.entity.Taluka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalukaRepository extends JpaRepository<Taluka, Long> {

    List<Taluka> findByDistrictId(Long districtId);

}