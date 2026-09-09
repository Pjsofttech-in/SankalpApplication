package com.sankalpapp.dynamicProfile.serviceImpl;

import com.sankalpapp.dynamicProfile.entity.ResultsPdf;
import com.sankalpapp.dynamicProfile.service.ResultsPdfService;
import com.sankalpapp.dynamicProfile.repository.ResultsPdfRepository;
import com.sankalpapp.serviceimpl.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultsPdfServiceImpl implements ResultsPdfService {

    private static final String folder = "ResultsPdf";
    private final ResultsPdfRepository resultsPdfRepository;
    private final S3Service s3Service;

    @Override
    public List<ResultsPdf> getAllResultsPdfs() {
        return resultsPdfRepository.findAll();
    }

    @Override
    public ResultsPdf getResultsPdfById(Long id) {
        return resultsPdfRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Results PDF not found with id: " + id));
    }

    @Override
    public ResultsPdf createResultsPdf(ResultsPdf resultsPdf, MultipartFile filePdf) {
        uploadFile(filePdf, resultsPdf);
        return resultsPdfRepository.save(resultsPdf);
    }

    private void uploadFile(MultipartFile pdf, ResultsPdf obj) {
        if (pdf != null) {
            try {
                String fileURL = s3Service.uploadFile(pdf, folder);
                obj.setPdfLink(fileURL);
            } catch (IOException e) {
                throw new RuntimeException("Unable to upload File");
            }
        }
    }

    @Override
    public ResultsPdf updateResultsPdf(Long id, ResultsPdf resultsPdf, MultipartFile filePdf) {

        ResultsPdf existingPdf = resultsPdfRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Results PDF not found with id: " + id));

        existingPdf.setTitle(resultsPdf.getTitle());
        existingPdf.setPdfLink(resultsPdf.getPdfLink());

        uploadFile(filePdf, existingPdf);

        return resultsPdfRepository.save(existingPdf);
    }

    @Override
    public void deleteResultsPdf(Long id) {
        ResultsPdf byId = resultsPdfRepository.findById(id).orElseThrow();
        s3Service.deleteFileByUrl(byId.getPdfLink());
        resultsPdfRepository.delete(byId);
    }
}