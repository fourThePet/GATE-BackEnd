package com.ureca.gate.dog.application;

import com.ureca.gate.dog.application.outputport.DogRepository;
import com.ureca.gate.dog.controller.inputport.ProfileService;
import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.global.exception.custom.BusinessException;
import com.ureca.gate.global.exception.errorcode.CommonErrorCode;
import com.ureca.gate.global.util.file.FileStorageService;
import com.ureca.gate.global.util.file.UploadFile;
import com.ureca.gate.plan.application.outputport.PlanDogRepository;
import com.ureca.gate.plan.domain.PlanDog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    @Value("${file.dir.dog-profile}")
    private String fileDir;

    private final DogRepository dogRepository;
    private final FileStorageService fileStorageService;
    private final PlanDogRepository planDogRepository;

    @Override
    public List<Dog> getAll(Long memberId) {
        return dogRepository.findByMemberId(memberId);
    }

    @Override
    public Dog getById(Long dogId) {
        return dogRepository.findById(dogId).orElseThrow(() -> new BusinessException(CommonErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public Dog create(Long memberId, ProfileSaveRequest request, MultipartFile imageFile) throws IOException {
        UploadFile uploadFile = fileStorageService.storeFile(memberId, imageFile, fileDir);
        Dog dog = Dog.from(memberId, request, uploadFile);
        return dogRepository.save(dog);
    }

    @Override
    public Dog update(Long memberId, Long dogId, ProfileSaveRequest profileSaveRequest, MultipartFile imageFile) throws IOException {
        Dog dog = getById(dogId);
        fileStorageService.deleteFile(memberId, dog.getUploadFile(), fileDir);
        UploadFile uploadFile = fileStorageService.storeFile(memberId, imageFile, fileDir);
        return dogRepository.save(dog.update(profileSaveRequest, uploadFile));
    }

    @Override
    public String imageUrl(Long memberId, UploadFile uploadFile) {
        return fileStorageService.generateFileUrl(memberId, uploadFile, fileDir);
    }

    @Override
    public void delete(Long dogId) {
        Dog dog = getById(dogId);
        dogRepository.delete(dog);
        List<PlanDog> planDogs = planDogRepository.findAllByDogId(dogId);
        planDogRepository.deleteAll(planDogs);
    }
}
