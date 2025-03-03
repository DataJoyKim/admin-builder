package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.restclient.code.MessageDataType;
import com.datajoy.admin_builder.apibuilder.restclient.code.ValueType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table
@Entity
public class RestClientBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="CLIENT_ID", nullable = false)
    private Long clientId;

    @Column(nullable = false, length = 100)
    private String paramName;

    @Column(length = 100)
    private String parentParamName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private MessageDataType dataType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private ValueType valueType;

    @Column(length = 300)
    private String inputValue;

    @Column
    private Integer orderNum;

    public static Map<String, RestClientBody> toBodyMetaMap(List<RestClientBody> bodyMeta) {
        Map<String, RestClientBody> bodyMetaMap = new HashMap<>();
        for(RestClientBody m : bodyMeta){
            bodyMetaMap.put(m.getParamName(), m);
        }

        return bodyMetaMap;
    }
}
