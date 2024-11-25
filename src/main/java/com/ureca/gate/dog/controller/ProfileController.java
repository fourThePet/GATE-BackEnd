package com.ureca.gate.dog.controller;

import com.ureca.gate.dog.controller.inputport.ProfileService;
import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.controller.response.ProfileResponse;
import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.global.dto.response.SuccessResponse;
import com.ureca.gate.global.util.file.FileStore;
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
    private final FileStore fileStore;

    @Operation(summary = "dog profile", description = "반려견 프로필")
    @GetMapping("/profile/{id}")
    public SuccessResponse<Object> dog(@PathVariable("id") Long id) {
        return SuccessResponse.success(
                ProfileResponse.builder()
                        .id(id)
                        .nickname("댕댕이")
                        .birthYmd("2024-06-10")
                        .age(0)
                        .genderName("여아")
                        .sizeName("소형")
                        .build());
    }

    @Operation(summary = "create dog profile", description = "반려견 프로필 생성")
    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SuccessResponse<Object> create(@AuthenticationPrincipal Long userId,
                                          @RequestPart ProfileSaveRequest profileSaveRequest,
                                          @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
        UploadFile uploadFile = fileStore.storeFile(userId, imageFile, fileDir);
        Dog dog = profileService.create(userId, profileSaveRequest, uploadFile);
        return SuccessResponse.success(dog);
    }
}
