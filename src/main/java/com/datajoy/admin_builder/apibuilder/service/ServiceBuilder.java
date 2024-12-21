package com.datajoy.admin_builder.apibuilder.service;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(name="SERVICE_BUILDER_UQ",columnNames={"SERVICE_NAME"})})
@Entity
public class ServiceBuilder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String serviceName;

    @Column(nullable = false, length = 200)
    private String displayName;

    @Column(length = 500)
    private String note;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SERVICE_ID")
    private List<ServiceFunction> serviceFunctions = new ArrayList<>();
}
