package com.datajoy.admin_builder.apibuilder.entity;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EntityConfig {
    private String statusParamKeyName = "_status";
    private String seqParamKeyName = "_seq";
}
