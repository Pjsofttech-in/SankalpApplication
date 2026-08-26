package com.sankalpapp.serviceimpl;

import io.micrometer.common.util.StringUtils;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.cloudfront.domain}")
    private String cloudFrontDomain;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file, String folder) throws IOException {

        String originalFileName = file.getOriginalFilename();

        String fileName = UUID.randomUUID() + "-" + originalFileName;

        String key = folder + "/" + fileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(key).contentType(file.getContentType()).build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        return "https://" + cloudFrontDomain + "/" + key;
    }

    public void deleteFile(String key) {
        if (StringUtils.isNotBlank(key)) {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(key).build();

            s3Client.deleteObject(deleteObjectRequest);
        }
    }

    public void deleteFileByUrl(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return;
        }
        String key = getKeyFromCloudFrontURL(fileUrl);
        deleteFile(key);
    }

    private @NonNull String getKeyFromCloudFrontURL(String fileUrl) {
        String prefix = "https://" + cloudFrontDomain + "/";

        if (!fileUrl.startsWith(prefix)) {
            throw new IllegalArgumentException("Invalid CloudFront URL");
        }

        return fileUrl.substring(prefix.length());
    }
}