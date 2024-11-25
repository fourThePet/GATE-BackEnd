package com.ureca.gate.dog.controller;

import com.ureca.gate.dog.controller.inputport.ProfileService;
import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.controller.response.ProfileResponse;
import com.ureca.gate.global.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Dog API", description = "반려견 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/dogs")
@RestController
public class ProfileController {

    private final ProfileService profileService;

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
    @PostMapping("/profile")
    public SuccessResponse<Object> create(@AuthenticationPrincipal Long userId, @RequestBody ProfileSaveRequest profileSaveRequest) {
        profileService.create(userId, profileSaveRequest);
        return SuccessResponse.success(null);
    }
}
