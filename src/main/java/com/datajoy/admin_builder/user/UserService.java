package com.datajoy.admin_builder.user;

import com.datajoy.core.crypto.PasswordEncoder;
import com.datajoy.core.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserGroupUserRepository userGroupUserRepository;
    private final PasswordEncoder passwordEncoder;
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

    public User signUp(SignUpRequest signUpRequest) throws BusinessException {
        Optional<User> savedUser = userRepository.findByLoginId(signUpRequest.getLoginId());
        if(savedUser.isPresent()){
            throw new BusinessException(UserErrorMessage.ALREADY_EXIST_USER);
        }

        User user = User.signUp(passwordEncoder, signUpRequest);

        return userRepository.save(user);
    }
}
