package com.ureca.gate.global.util.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@RequiredArgsConstructor
@Component
public class S3FileManager {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    public void uploadFile(MultipartFile multipartFile, String filePath) throws IOException {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(filePath)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, multipartFile.getSize()));
        }
    }

    public void deleteFile(String filePath) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(filePath)
                .build();

        s3Client.deleteObject(deleteObjectRequest);
    }

    public String generateFileUrl(String filePath) {
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(filePath)
                .build();

        URL fileUrl = s3Client.utilities().getUrl(getUrlRequest);
        return fileUrl.toString();
    }
}
