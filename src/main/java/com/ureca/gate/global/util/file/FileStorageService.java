package com.ureca.gate.global.util.file;

import com.ureca.gate.global.util.s3.S3FileManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FileStorageService {

    private final S3FileManager s3FileManager;

    public String generateFileUrl(Long userId, UploadFile uploadFile, String imageType) {
        if (uploadFile == null) {
            return null;
        }
        String filePath = createFilePath(userId, uploadFile.getStoreFileName(), imageType);
        return s3FileManager.generateFileUrl(filePath);
    }

    public UploadFile storeFile(Long userId, MultipartFile multipartFile, String imageType) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String filePath = createFilePath(userId, storeFileName, imageType);

        s3FileManager.uploadFile(multipartFile, filePath);

        return new UploadFile(originalFilename, storeFileName);
    }

    public void deleteFile(Long userId, UploadFile uploadFile, String imageType) {
        if (uploadFile == null) {
            return;
        }
        String filePath = createFilePath(userId, uploadFile.getStoreFileName(), imageType);
        s3FileManager.deleteFile(filePath);
    }

    private String createFilePath(Long userId, String fileName, String imageType) {
        return "user_" + userId + "/" + imageType + "/" + fileName;
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
