package interview.user_service.service.impl;

import interview.user_service.dto.request.UpdateRoleRequest;
import interview.user_service.dto.request.UserLoginRequest;
import interview.user_service.dto.request.UserRegistrationRequest;
import interview.user_service.dto.response.UserResponse;
import interview.user_service.entity.Role;
import interview.user_service.entity.User;
import interview.user_service.exception.CustomException;
import interview.user_service.mapper.UserMapper;
import interview.user_service.repository.UserRepository;
import interview.user_service.service.EmailService;
import interview.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException("Username already exists", HttpStatus.CONFLICT);
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException("Email already exists", HttpStatus.CONFLICT);
        }

        // Create new user
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setEmail(request.getEmail());

        // Save user
        User savedUser = userRepository.save(user);

        // Send registration email
        try {
            emailService.sendRegistrationEmail(savedUser);
        } catch (Exception e) {
            // Log the error but don't stop the registration process
            System.err.println("Failed to send registration email: " + e.getMessage());
        }

        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse loginUser(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException("Invalid username or password", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUserRole(Long userId, UpdateRoleRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));

        // Prevent changing the role of the default admin
        if (user.getUsername().equals("admin")) {
            throw new CustomException("Cannot change the role of the default admin user", HttpStatus.FORBIDDEN);
        }

        user.setRole(request.getRole());
        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }
}