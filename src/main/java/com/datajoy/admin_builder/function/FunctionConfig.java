package com.datajoy.admin_builder.function;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FunctionConfig {
    private String requestMessageSeqKey = "_seq";
    private String requestMessageRestClientRequestBodyKey = "_requestBody";
}
