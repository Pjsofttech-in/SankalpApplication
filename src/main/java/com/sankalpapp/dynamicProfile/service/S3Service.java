package com.sankalpapp.dynamicProfile.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class S3Service {

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    private final S3Client s3Client;

    private static final String SYSTEM_NAME = "webclient-sys";
    private static final String FOLDER_NAME = "doct";

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // ✅ Upload a single image to 'doct/' folder with UUID file name
    public String uploadImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is missing or empty");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isBlank()) {
            throw new IllegalArgumentException("File must have a valid name");
        }

        // Use original file name
        String fileKey = "/webclient-sys/doct/" + originalFilename;

        // Save file temporarily
        java.nio.file.Path tempPath = Files.createTempFile("upload-", originalFilename);
        file.transferTo(tempPath.toFile());

        // Upload to S3
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromFile(tempPath));

        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileKey;
    }



    // ✅ Delete image from S3 (from doct folder)
    public void deleteImage(String fileUrl) {
        try {
            // Extract S3 key from URL
            String key = fileUrl.substring(fileUrl.indexOf(SYSTEM_NAME + "/"));

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
            System.out.println("Successfully deleted image from S3: " + key);

        } catch (Exception e) {
            System.err.println("Failed to delete image from S3: " + e.getMessage());
            throw new RuntimeException("Failed to delete image from S3", e);
        }
    }
}