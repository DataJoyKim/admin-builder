package com.datajoy.admin_builder.customcode;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="CUSTOM_CODE_UQ",columnNames={"codeName"})})
@Entity
public class CustomCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String codeName;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String script;
}
