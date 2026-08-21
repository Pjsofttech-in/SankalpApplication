package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebSlideBar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SlideBarRepository extends JpaRepository<WebSlideBar, Long> {

    @Query("SELECT s FROM WebSlideBar s ORDER BY s.id DESC")
    List<WebSlideBar> findAllOrderById();

}