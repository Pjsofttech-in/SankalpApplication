package com.sankalpapp.dynamicProfile.service;

import com.sankalpapp.dynamicProfile.entity.ResultsPdf;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResultsPdfService {

    List<ResultsPdf> getAllResultsPdfs();

    ResultsPdf getResultsPdfById(Long id);

    ResultsPdf createResultsPdf(ResultsPdf resultsPdf, MultipartFile resultPdf);

    ResultsPdf updateResultsPdf(Long id, ResultsPdf resultsPdf, MultipartFile resultPdf);

    void deleteResultsPdf(Long id);
}