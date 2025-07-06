package com.datajoy.admin_builder.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public User signUp(SignUpRequest signUpRequest) {
        Optional<User> savedUser = userRepository.findByLoginId(signUpRequest.getLoginId());
        if(savedUser.isPresent()){
            //TODO throw
        }

        User user = User.signUp(signUpRequest);

        return user;
    }
}
