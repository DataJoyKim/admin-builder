package com.datajoy.admin_builder.apibuilder.restclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter @AllArgsConstructor @Builder
public class RestClientResult {
    ResponseEntity<Object> response;
}
