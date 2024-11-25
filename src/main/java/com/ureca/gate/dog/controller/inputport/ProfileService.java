package com.ureca.gate.dog.controller.inputport;

import com.ureca.gate.dog.controller.request.ProfileSaveRequest;
import com.ureca.gate.dog.domain.Dog;

public interface ProfileService {
    Dog create(Long userId, ProfileSaveRequest request);
}
