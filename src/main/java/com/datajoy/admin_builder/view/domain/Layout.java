package com.datajoy.admin_builder.view.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class Layout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String logoText;
    @Column
    private Boolean useAuthValidation = true;
    @Column
    private Boolean useProfile = true;
    @Column
    private Boolean useLogo = true;
    @Column(length = 10)
    private String logoBackgroundColor;
    @Column(length = 100)
    private String logoLink;
    @Column(length = 100)
    private String logoImg;
    @Column(length = 100)
    private String layoutTitle;

    public void update(
            Boolean useAuthValidation,
            Boolean useProfile,
            Boolean useLogo,
            String logoText,
            String logoBackgroundColor,
            String logoLink,
            String logoImg,
            String layoutTitle
    ) {
        this.useAuthValidation = useAuthValidation;
        this.useProfile = useProfile;
        this.useLogo = useLogo;
        this.logoText = logoText;
        this.logoBackgroundColor = logoBackgroundColor;
        this.logoLink = logoLink;
        this.logoImg = logoImg;
        this.layoutTitle = layoutTitle;
    }
}
