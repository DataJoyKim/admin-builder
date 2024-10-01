package com.datajoy.web_builder.persistence.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor @Builder
public class PrimaryKey {
    private String key;
    private Boolean autoIncrement;
}
