package com.datajoy.admin_builder.security;

import com.datajoy.admin_builder.user.UserGroup;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class UserGroupAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Boolean lowerAuthorityGrant;

    @ManyToOne
    @JoinColumn(name = "AUTHORITY_CODE")
    private Authority authority;

    @ManyToOne
    @JoinColumn(name = "USER_GROUP_CODE")
    private UserGroup userGroup;
}
