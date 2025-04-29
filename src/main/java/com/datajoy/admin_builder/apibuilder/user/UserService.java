package com.datajoy.admin_builder.apibuilder.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow();
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow();
    }
}
