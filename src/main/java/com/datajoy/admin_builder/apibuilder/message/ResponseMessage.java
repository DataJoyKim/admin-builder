package com.datajoy.admin_builder.apibuilder.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @AllArgsConstructor @Builder
public class ResponseMessage<T> {
    private T contents;
    private String status;
    private String message;

    public static ResponseMessage<?> createMessage(Object contents) {
        return ResponseMessage.builder()
                .contents(contents)
                .build();
    }
}
