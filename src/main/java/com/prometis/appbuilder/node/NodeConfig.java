package com.prometis.appbuilder.node;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NodeConfig {
    private String requestMessageSeqKey = "_seq";
    private String requestMessageRestClientRequestBodyKey = "_requestBody";
}
