package com.ureca.gate.global.util.file;

import com.ureca.gate.global.util.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class FileStore {

    private final S3Uploader s3Uploader;

    public UploadFile storeFile(Long userId, MultipartFile multipartFile, String imageType) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        String filePath = createFilePath(userId, storeFileName, imageType);

        s3Uploader.uploadFile(multipartFile, filePath);

        return new UploadFile(originalFilename, storeFileName);
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
