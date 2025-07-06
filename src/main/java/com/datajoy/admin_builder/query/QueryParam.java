package com.datajoy.admin_builder.query;

import com.datajoy.admin_builder.query.code.AutoValueType;
import com.datajoy.admin_builder.query.code.InOut;
import com.datajoy.admin_builder.query.code.ParamType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class QueryParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="QUERY_ID", nullable = false)
    private Long queryId;

    @Column(nullable = false, length = 200)
    private String paramName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private ParamType paramType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private AutoValueType autoValueType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private InOut inOut;
}
