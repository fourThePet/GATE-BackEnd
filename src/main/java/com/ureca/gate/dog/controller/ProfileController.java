package com.ureca.gate.dog.controller;

import com.ureca.gate.dog.controller.inputport.ProfileService;
import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.controller.response.ProfileResponse;
import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.global.util.file.FileStorageService;
import com.ureca.gate.global.util.file.UploadFile;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Dog API", description = "반려견 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/dogs")
@RestController
public class ProfileController {

    @Value("${file.dir.dog-profile}")
    private String fileDir;

    private final ProfileService profileService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "반려견 프로필 조회 API", description = "반려견 프로필 조회")
    @GetMapping("/profile/{dogId}")
    public SuccessResponse<ProfileResponse> dog(@AuthenticationPrincipal Long userId,
                                                @PathVariable("dogId") Long dogId) {
        Dog dog = profileService.getById(dogId);
        String imageUrl = fileStorageService.generateFileUrl(userId, dog.getUploadFile().getStoreFileName(), fileDir);
        ProfileResponse response = ProfileResponse.from(dog, imageUrl);
        return SuccessResponse.success(response);
    }

    @Operation(summary = "반려견 프로필 등록 API", description = "multipart/form-data 타입의 데이터의 소비를 설정하여, 1개의 이미지 파일과 반려견 프로필을 한번에 등록")
    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Object> create(@AuthenticationPrincipal Long userId,
                                          @RequestPart ProfileSaveRequest profileSaveRequest,
                                          @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        UploadFile uploadFile = fileStorageService.storeFile(userId, imageFile, fileDir);
        Dog dog = profileService.create(userId, profileSaveRequest, uploadFile);
        return SuccessResponse.success(dog);
    }
}
