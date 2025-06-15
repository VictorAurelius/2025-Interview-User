package interview.user_service.service;

import interview.user_service.entity.User;

public interface EmailService {
    void sendRegistrationEmail(User user);
}