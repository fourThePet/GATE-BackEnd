package com.ureca.gate.dog.controller.inputport;

import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.global.util.file.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProfileService {
    List<Dog> getAll(Long memberId);

    Dog getById(Long dogId);

    Dog create(Long memberId, ProfileSaveRequest profileSaveRequest, MultipartFile imageFile) throws IOException;

    Dog update(Long memberId, Long dogId, ProfileSaveRequest profileSaveRequest, MultipartFile imageFile) throws IOException;

    String imageUrl(Long memberId, UploadFile uploadFile);

    void delete(Long dogId);
}
