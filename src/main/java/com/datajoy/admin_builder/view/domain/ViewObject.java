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
    @Column(nullable = false, length = 100)
    private ObjectType type;
    @Column(nullable = false, length = 100)
    private String path;
}
