package com.datajoy.admin_builder.restclient;

import com.datajoy.admin_builder.restclient.code.ValueType;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table
@Entity
public class RestClientQueryParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="CLIENT_ID", nullable = false)
    private Long clientId;

    @Column(nullable = false, length = 100)
    private String paramName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private ValueType valueType;

    @Column(length = 300)
    private String inputValue;
}
