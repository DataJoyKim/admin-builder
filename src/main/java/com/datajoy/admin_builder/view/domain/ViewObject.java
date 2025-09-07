package com.datajoy.admin_builder.view.domain;

import com.datajoy.admin_builder.view.code.ObjectType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class ViewObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String objectCd;
    @Column(nullable = false, length = 200)
    private String objectNm;
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ObjectType type;
    @Column(length = 100)
    private String path;
    @Column
    private Boolean useAuthValidation = true;
    @Column
    private Boolean useAuthorityValidation = true;

    public void update(
            String objectCd,
            String objectNm,
            ObjectType type,
            String path
    ) {
        this.objectCd = objectCd;
        this.objectNm = objectNm;
        this.type = type;
        this.path = path;
    }
}
