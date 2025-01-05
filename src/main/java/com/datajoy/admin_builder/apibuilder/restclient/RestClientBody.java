package com.datajoy.admin_builder.apibuilder.restclient;

import com.datajoy.admin_builder.apibuilder.restclient.code.MessageDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class RestClientBody {
    private String parameterName;
    private String parentParameterName;
    private MessageDataType dataType;
    private String autoValueType;
    private Integer orderNum;
}
