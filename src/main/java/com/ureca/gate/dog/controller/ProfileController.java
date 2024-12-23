package com.ureca.gate.dog.controller;

import com.ureca.gate.dog.controller.inputport.ProfileService;
import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.controller.response.ProfileResponse;
import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.global.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Dog API", description = "반려견 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/dogs")
@RestController
public class ProfileController {

    private final ProfileService profileService;

    @Operation(summary = "반려견 프로필 리스트 조회 API", description = "나의 반려견 프로필 리스트 조회")
    @GetMapping("/profiles")
    public SuccessResponse<List<ProfileResponse>> getAll(@AuthenticationPrincipal Long memberId) {
        List<Dog> dogs = profileService.getAll(memberId);
        List<ProfileResponse> result = dogs.stream()
                .map(dog -> ProfileResponse.from(dog, profileService.imageUrl(memberId, dog.getUploadFile())))
                .collect(Collectors.toList());
        return SuccessResponse.success(result);
    }

    @Operation(summary = "반려견 프로필 조회 API", description = "반려견 프로필과 이미지 파일 url 조회")
    @GetMapping("/profile/{dogId}")
    public SuccessResponse<ProfileResponse> getById(@AuthenticationPrincipal Long memberId,
                                                    @PathVariable("dogId") Long dogId) {
        Dog dog = profileService.getById(dogId);
        String imageUrl = profileService.imageUrl(memberId, dog.getUploadFile());
        ProfileResponse response = ProfileResponse.from(dog, imageUrl);
        return SuccessResponse.success(response);
    }

    @Operation(summary = "반려견 프로필 등록 API", description = "multipart/form-data 타입의 데이터의 소비를 설정하여 1개의 이미지 파일과 반려견 프로필을 등록하고, 반려견 프로필 정보 반환 (이미지 미포함)")
    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<ProfileResponse> create(@AuthenticationPrincipal Long memberId,
                                                   @RequestPart ProfileSaveRequest profileSaveRequest,
                                                   @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        Dog dog = profileService.create(memberId, profileSaveRequest, imageFile);
        return SuccessResponse.success(ProfileResponse.from(dog));
    }

    @Operation(summary = "반려견 프로필 수정 API", description = "multipart/form-data 타입의 데이터의 소비를 설정하여 1개의 이미지 파일과 반려견 프로필을 수정하고, 기존 이미지는 삭제하고, 반려견 프로필 정보 반환 (이미지 미포함)")
    @PutMapping(value = "/profile/{dogId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<ProfileResponse> update(@AuthenticationPrincipal Long memberId,
                                                   @PathVariable Long dogId,
                                                   @RequestPart ProfileSaveRequest profileSaveRequest,
                                                   @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        Dog dog = profileService.update(memberId, dogId, profileSaveRequest, imageFile);
        return SuccessResponse.success(ProfileResponse.from(dog));
    }

    @Operation(summary = "반려견 프로필 삭제 API", description = "반려견 프로필 정보와 이미지 삭제")
    @DeleteMapping("/profile/{dogId}")
    public SuccessResponse<Object> delete(@AuthenticationPrincipal Long memberId,
                                          @PathVariable("dogId") Long dogId) {
        profileService.delete(dogId);
        return SuccessResponse.successWithoutResult(null);
    }
}
