package interview.user_service.service;

import interview.user_service.dto.request.UserLoginRequest;
import interview.user_service.dto.request.UserRegistrationRequest;
import interview.user_service.dto.response.UserResponse;

public interface UserService {
    UserResponse registerUser(UserRegistrationRequest request);

    UserResponse loginUser(UserLoginRequest request);
}