package com.sankalpapp.dynamicProfile.repository;

import com.sankalpapp.dynamicProfile.entity.WebSecurityUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecurityUrlrepository extends JpaRepository<WebSecurityUrl, Long> {
    @Query("SELECT s FROM WebSecurityUrl s WHERE LOWER(TRIM(s.url)) = LOWER(TRIM(:url))")
    Optional<WebSecurityUrl> findByUrl(@Param("url") String url);
}
