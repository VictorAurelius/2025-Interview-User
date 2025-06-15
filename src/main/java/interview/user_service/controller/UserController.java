package interview.user_service.controller;

import interview.user_service.dto.request.UpdateRoleRequest;
import interview.user_service.dto.request.UserLoginRequest;
import interview.user_service.dto.request.UserRegistrationRequest;
import interview.user_service.dto.response.ApiResponse;
import interview.user_service.dto.response.UserResponse;
import interview.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {
        UserResponse userResponse = userService.registerUser(request);
        return ResponseEntity.ok(ApiResponse.success(
                "User registered successfully",
                userResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> loginUser(
            @Valid @RequestBody UserLoginRequest request) {
        UserResponse userResponse = userService.loginUser(request);
        return ResponseEntity.ok(ApiResponse.success(
                "Login successful",
                userResponse));
    }

    @PutMapping("/users/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserRole(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateRoleRequest request) {
        UserResponse userResponse = userService.updateUserRole(userId, request);
        return ResponseEntity.ok(ApiResponse.success(
                "User role updated successfully",
                userResponse));
    }
}