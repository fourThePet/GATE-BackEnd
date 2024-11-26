package com.ureca.gate.dog.controller.inputport;

import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.Dog;
import com.ureca.gate.global.util.file.UploadFile;

public interface ProfileService {
    Dog create(Long userId, ProfileSaveRequest profileSaveRequest, UploadFile uploadFile);
}
