package com.sankalpapp.repository;

import com.sankalpapp.entity.Taluka;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TalukaRepository extends JpaRepository<Taluka, Long> {

    List<Taluka> findByDistrictId(Long districtId);

}