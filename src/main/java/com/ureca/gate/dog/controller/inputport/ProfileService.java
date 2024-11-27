package com.ureca.gate.dog.controller.inputport;

import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.global.util.file.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProfileService {
    Dog getById(Long dogId);

    Dog create(Long userId, ProfileSaveRequest profileSaveRequest, MultipartFile imageFile) throws IOException;

    Dog update(Long userId, Long dogId, ProfileSaveRequest profileSaveRequest, MultipartFile imageFile) throws IOException;

    String imageUrl(Long userId, UploadFile uploadFile);
}
