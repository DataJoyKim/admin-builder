package com.datajoy.admin_builder.superadmin;

import com.datajoy.admin_builder.security.Authority;
import com.datajoy.admin_builder.security.AuthorityRepository;
import com.datajoy.admin_builder.security.UserGroupAuthority;
import com.datajoy.admin_builder.security.UserGroupAuthorityRepository;
import com.datajoy.admin_builder.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SuperAdminService {
    private static final String SYS_ADMIN = "SYS_ADMIN";
    private final AuthorityRepository authorityRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserGroupAuthorityRepository userGroupAuthorityRepository;
    private final UserGroupUserRepository userGroupUserRepository;
    private final UserRepository userRepository;

    public void createSysAdmin(String loginId) {
        Authority authority = Authority.builder()
                .code(SYS_ADMIN)
                .name(SYS_ADMIN)
                .build();

        Authority savedAuthority = authorityRepository.save(authority);

        UserGroup userGroup = UserGroup.builder()
                .code(SYS_ADMIN)
                .name(SYS_ADMIN)
                .build();

        UserGroup savedUserGroup = userGroupRepository.save(userGroup);

        UserGroupAuthority userGroupAuthority = UserGroupAuthority.builder()
                .authority(savedAuthority)
                .userGroup(savedUserGroup)
                .build();

        userGroupAuthorityRepository.save(userGroupAuthority);

        User user = userRepository.findByLoginId(loginId).orElseThrow();

        UserGroupUser userGroupUser = UserGroupUser.builder()
                .user(user)
                .userGroup(savedUserGroup)
                .build();

        userGroupUserRepository.save(userGroupUser);
    }
}
