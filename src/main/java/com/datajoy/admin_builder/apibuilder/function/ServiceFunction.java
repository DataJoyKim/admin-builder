package com.datajoy.admin_builder.apibuilder.function;

import com.datajoy.admin_builder.apibuilder.function.code.ErrorResolveType;
import com.datajoy.admin_builder.apibuilder.function.code.FunctionType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class ServiceFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SERVICE_ID", nullable = false)
    private Long serviceId;

    @Column(nullable = false, length = 100)
    private String functionName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private FunctionType functionType;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ErrorResolveType errorResolveType;

    @Column
    private Integer orderNum;

    @Column
    private Boolean isLogging;

    @Column(length = 100)
    private String requestMessageId;

    @Column(length = 100)
    private String responseMessageId;
}
