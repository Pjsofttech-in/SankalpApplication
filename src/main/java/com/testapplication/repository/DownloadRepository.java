package com.testapplication.repository;

import com.testapplication.entity.Download;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Download, Long> {

}