package com.prometis.appbuilder.entity;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EntityConfig {
    private String statusParamKeyName = "_status";
    private String seqParamKeyName = "_seq";
}
