package com.datajoy.admin_builder.view.domain;

import com.datajoy.admin_builder.view.code.ObjectType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="VIEW_OBJECT_UQ",columnNames={"objectCode"})})
@Entity
public class ViewObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String objectCode;
    @Column(nullable = false, length = 200)
    private String objectName;
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
            String objectCode,
            String objectName,
            ObjectType type,
            String path,
            Boolean useAuthValidation,
            Boolean useAuthorityValidation
    ) {
        this.objectCode = objectCode;
        this.objectName = objectName;
        this.type = type;
        this.path = path;
        this.useAuthValidation = useAuthValidation;
        this.useAuthorityValidation = useAuthorityValidation;
    }
}
