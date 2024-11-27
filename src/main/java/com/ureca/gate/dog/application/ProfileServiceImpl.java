package com.ureca.gate.dog.application;

import com.ureca.gate.dog.application.outputport.DogRepository;
import com.ureca.gate.dog.controller.inputport.ProfileService;
import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.global.util.file.FileStorageService;
import com.ureca.gate.global.util.file.UploadFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    @Value("${file.dir.dog-profile}")
    private String fileDir;

    private final DogRepository dogRepository;
    private final FileStorageService fileStorageService;

    @Override
    public Dog getById(Long dogId) {
        return dogRepository.findById(dogId).orElseThrow(() -> new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public Dog create(Long userId, ProfileSaveRequest request, MultipartFile imageFile) throws IOException {
        UploadFile uploadFile = fileStorageService.storeFile(userId, imageFile, fileDir);
        Dog dog = Dog.from(userId, request, uploadFile);
        return dogRepository.save(dog);
    }

    @Override
    public Dog update(Long userId, Long dogId, ProfileSaveRequest profileSaveRequest, MultipartFile imageFile) throws IOException {
        Dog dog = getById(dogId);
        fileStorageService.deleteFile(userId, dog.getUploadFile(), fileDir);
        UploadFile uploadFile = fileStorageService.storeFile(userId, imageFile, fileDir);
        return dogRepository.save(dog.update(profileSaveRequest, uploadFile));
    }

    @Override
    public String imageUrl(Long userId, UploadFile uploadFile) {
        return fileStorageService.generateFileUrl(userId, uploadFile, fileDir);
    }
}
