package com.datajoy.admin_builder.apibuilder.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserGroupUserRepository userGroupUserRepository;
    public User getUserByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow();
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow();
    }

    public List<UserGroupUser> getUserGroupUser(Long userId) {
        return userGroupUserRepository.findByUserId(userId);
    }
}
